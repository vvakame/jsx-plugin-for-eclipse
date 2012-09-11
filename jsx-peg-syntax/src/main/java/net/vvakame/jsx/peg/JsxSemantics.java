package net.vvakame.jsx.peg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import mouse.runtime.Phrase;
import mouse.runtime.PhraseExtend;
import mouse.runtime.SemanticsBase;

public class JsxSemantics extends SemanticsBase {

	Tree current;

	Map<String, Tree> treeHolder = new LinkedHashMap<String, Tree>();

	public static class Tree implements Comparable<Tree> {
		Tree parent;
		Phrase lhs; // this Token
		PhraseExtend lhsExtend;
		LinkedHashSet<Tree> rhs = new LinkedHashSet<Tree>(); // child Token
		List<Phrase> rhsRaw = new ArrayList<Phrase>();

		@Override
		public int compareTo(Tree o) {
			return lhsExtend.getStart() - o.lhsExtend.getStart();
		}

		@Override
		public String toString() {
			return "Tree [lhs=" + lhs.rule() + "]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((lhs == null) ? 0 : lhs.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Tree other = (Tree) obj;
			if (lhs == null) {
				if (other.lhs != null)
					return false;
			} else if (!lhs.equals(other.lhs))
				return false;
			return true;
		}
	}

	public void construct() {
		final String parentRuleName = getParentRuleName();
		Tree parent = treeHolder.get(parentRuleName);
		if (parent == null) {
			parent = new Tree();
			treeHolder.put(parentRuleName, parent);
		}

		final String currentRuleName = getCurrentRuleName();
		Tree current = treeHolder.get(currentRuleName);
		if (current == null) {
			current = new Tree();
		} else {
			treeHolder.remove(currentRuleName);
		}
		current.parent = parent;
		current.lhs = lhs();
		current.lhsExtend = PhraseExtend
				.get((mouse.runtime.ParserBase.Phrase) lhs());

		parent.rhs.add(current);

		for (int i = 0; i < rhsSize(); i++) {
			current.rhsRaw.add(rhs(i));
		}

		this.current = current;
	}

	private String getCurrentRuleName() {
		return lhs().rule();
	}

	private String getParentRuleName() {
		// FIXME we can take any implementations? this is slow!
		boolean current = false;

		StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
		for (int i = 0; i < stacks.length; i++) {
			StackTraceElement stack = stacks[i];

			// subRule contains "_" (for example, "importStatement_2")
			if (current == true && stack.getMethodName().contains("_") == false) {
				return stack.getMethodName();
			} else if (stack.getMethodName().equals(lhs().rule())) {
				current = true;
			}
		}
		return null;
	}

	@Override
	public Phrase lhs() {
		return super.lhs();
	}

	@Override
	public int rhsSize() {
		return super.rhsSize();
	}

	@Override
	public Phrase rhs(int i) {
		return super.rhs(i);
	}

	@Override
	public String rhsText(int i, int j) {
		return super.rhsText(i, j);
	}

	@Deprecated
	public Tree getTree() {
		return current;
	}

	public SyntaxTreeMouseImpl getSyntaxTree() {
		cleanUpInvalidBranch(current);
		fixMemoizedBranch(current);
		sortBranch(current);
		cleanUpFailedBranch(current);
		cleanUpEmptyBranch(current);

		Debug.dump(current);

		SyntaxTreeMouseImpl parent = new SyntaxTreeMouseImpl();
		convert(current, parent);

		return (SyntaxTreeMouseImpl) parent.children.get(0);
	}

	public SyntaxTreeMouseImpl getSyntaxTreeWithCompress() {
		cleanUpInvalidBranch(current);
		fixMemoizedBranch(current);
		sortBranch(current);
		cleanUpFailedBranch(current);
		cleanUpEmptyBranch(current);

		compressBranch(current);
		Debug.dump(current);

		SyntaxTreeMouseImpl parent = new SyntaxTreeMouseImpl();
		convert(current, parent);

		return (SyntaxTreeMouseImpl) parent.children.get(0);
	}

	private void fixMemoizedBranch(Tree tree) {
		final Map<Phrase, Tree> cache = new HashMap<Phrase, Tree>();

		// missing branch
		for (String parentName : treeHolder.keySet()) {
			Tree parent = treeHolder.get(parentName);

			for (Tree rhs : parent.rhs) {
				cache.put(rhs.lhs, rhs);
			}
		}

		new TreeVisitor() {

			@Override
			public void lhs(Tree tree, int depth) {
				if (!cache.containsKey(tree.lhs)) {
					cache.put(tree.lhs, tree);
				}

				for (Phrase rhs : tree.rhsRaw) {
					if (cache.containsKey(rhs)) {
						tree.rhs.add(cache.get(rhs));
					} else if (treeHolder.containsKey(rhs)) {
						tree.rhs.add(treeHolder.get(rhs));
					}
				}
			}
		}.visit(tree);
	}

	private void cleanUpInvalidBranch(Tree tree) {
		new TreeVisitor() {
			@Override
			public void lhs(Tree tree, int depth) {
				List<Tree> removeList = new ArrayList<Tree>();

				for (Tree rhs : tree.rhs) {
					if (tree.lhsExtend.getStart() > rhs.lhsExtend.getStart()) {
						removeList.add(rhs);
					}
				}

				Tree[] rhsArray = tree.rhs.toArray(new Tree[0]);
				for (int i = 0; i < rhsArray.length; i++) {
					PhraseExtend current = rhsArray[i].lhsExtend;

					for (int j = i + 1; j < rhsArray.length; j++) {
						PhraseExtend next = rhsArray[j].lhsExtend;

						if (current.getStart() <= next.getStart()
								&& current.getEnd() >= next.getEnd()) {
							removeList.add(rhsArray[j]);
						}
					}
				}

				tree.rhs.removeAll(removeList);
			}
		}.visit(tree);
	}

	private void cleanUpEmptyBranch(Tree tree) {
		new TreeVisitor() {
			@Override
			public void lhs(Tree tree, int depth) {
				final List<Tree> removeList = new ArrayList<Tree>();
				for (final Tree rhs : tree.rhs) {
					final PhraseExtend extend = rhs.lhsExtend;
					if (extend.getStart() == extend.getEnd()) {
						removeList.add(rhs);

						new TreeVisitor() {
							@Override
							public void lhs(Tree tree, int depth) {
								if (extend.getStart() != tree.lhsExtend
										.getEnd()) {
									removeList.remove(rhs);
								}
							}
						}.visit(rhs);
					}
				}
				tree.rhs.removeAll(removeList);
			}
		}.visit(tree);
	}

	private void cleanUpFailedBranch(Tree tree) {
		new TreeVisitor() {
			@Override
			public void lhs(Tree tree, int depth) {
				List<Tree> removeList = new ArrayList<Tree>();

				Tree[] rhsArray = tree.rhs.toArray(new Tree[0]);

				ROOT: for (int i = 0; i < rhsArray.length; i++) {
					PhraseExtend current = rhsArray[i].lhsExtend;

					for (int j = i + 1; j < rhsArray.length; j++) {
						PhraseExtend next = rhsArray[j].lhsExtend;

						if (current.getStart() == next.getStart()) {
							removeList.add(rhsArray[i]);
							continue ROOT;
						} else if (next.getEnd() < current.getEnd()) {
							removeList.add(rhsArray[j]);
							continue ROOT;
						}
					}
				}

				tree.rhs.removeAll(removeList);
			}
		}.visit(tree);
	}

	private void sortBranch(Tree tree) {
		new TreeVisitor() {
			@Override
			public void lhs(Tree tree, int depth) {
				Tree[] trees = tree.rhs.toArray(new Tree[0]);
				Arrays.sort(trees);
				tree.rhs.clear();
				for (Tree child : trees) {
					tree.rhs.add(child);
				}
			}
		}.visit(tree);
	}

	private void compressBranch(Tree tree) {
		new TreeVisitor() {
			@Override
			public void lhs(Tree tree, int depth) {
				if (tree.rhs.size() == 1
						&& tree.rhs.iterator().next().rhs.size() == 1) {

					PhraseExtend child = tree.rhs.iterator().next().lhsExtend;
					PhraseExtend grandChild = tree.rhs.iterator().next().rhs
							.iterator().next().lhsExtend;

					if (child.getStart() == grandChild.getStart()
							&& child.getEnd() == grandChild.getEnd()) {
						Tree grandChildTree = tree.rhs.iterator().next().rhs
								.iterator().next();
						tree.rhs.clear();
						tree.rhs.add(grandChildTree);
						// recursive
						lhs(tree, depth);
					}
				}
			}
		}.visit(tree);
	}

	public static abstract class TreeVisitor {
		public void visit(Tree tree) {
			visit(tree, 0);
		}

		private void visit(Tree tree, int depth) {
			lhs(tree, depth);

			LinkedHashSet<Tree> newSet = new LinkedHashSet<Tree>();
			{
				Iterator<Tree> iterator = tree.rhs.iterator();
				int i = 0;
				while (iterator.hasNext()) {
					Tree next = iterator.next();
					Tree newNext = rhs(i, next, depth);
					if (newNext != null) {
						newSet.add(newNext);
					} else {
						newSet.add(next);
					}
				}
			}
			{
				for (Tree next : tree.rhs) {
					visit(next, depth + 1);
				}
			}
			tree.rhs = newSet;
		}

		public abstract void lhs(Tree tree, int depth);

		public Tree rhs(int index, Tree tree, int depth) {
			return null;
		}
	}

	void convert(Tree tree, SyntaxTreeMouseImpl parent) {
		SyntaxTreeMouseImpl syntaxTree = new SyntaxTreeMouseImpl();

		PhraseExtend lhs = tree.lhsExtend;

		syntaxTree.index = lhs.getStart();
		syntaxTree.type = lhs.rule();

		Tree[] trees = tree.rhs.toArray(new Tree[0]);
		if (trees.length != 0) {
			int childStart = Integer.MAX_VALUE;
			int childEnd = 0;
			for (Tree rhs : tree.rhs) {
				PhraseExtend extend = rhs.lhsExtend;
				childStart = Math.min(childStart, extend.getStart());
				childEnd = Math.max(childEnd, extend.getEnd());
			}

			PhraseExtend rhs0 = trees[0].lhsExtend;
			int base = lhs.getStart();
			int len = rhs0.getStart() - base;
			if (childStart == lhs.getStart() && childEnd == lhs.getEnd()) {
				syntaxTree.name = "";
			} else if (len >= 0 && lhs.text().length() < len) {
				syntaxTree.name = lhs.text();
			} else if (len >= 0) {
				syntaxTree.name = lhs.text().substring(0, len);
			} else {
				syntaxTree.name = "";
			}
		} else if ("".equals(lhs.text())) {
			return;
		} else {
			syntaxTree.name = lhs.text();
		}

		parent.children.add(syntaxTree);

		ROOT: for (int i = 0; i < trees.length; i++) {
			Tree child = trees[i];
			final int startPos = child.lhsExtend.getStart();
			for (int j = i + 1; j < trees.length; j++) {
				int nextPos = trees[j].lhsExtend.getStart();
				if (startPos == nextPos) {
					continue ROOT;
				}
			}

			convert(child, syntaxTree);
		}
	}
}
