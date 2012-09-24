// test for 036.variant.jsx issue.

class Test {
	static function run() : void {
		var v : variant = null;
		v as string;
		// log (v as string).split("").join(","); // null may be "null" or "undefined"
	}
}
