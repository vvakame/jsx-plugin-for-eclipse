import "js.jsx";
import "js/web.jsx";

import "model.jsx";

/* MineSweeper を描画するためのクラス */
class MineSweeperRender implements OnMineSweeperEventListener {

  var game: MineSweeper;
  var parent: Element;
  
  /* 実行開始 */
  function run(parent: Element): void {
    this.parent = parent;
    while(parent.firstChild) {
      parent.removeChild(parent.firstChild);
    }
    this.game = new MineSweeper(this);
  }
  
  /* 盤が生成された時のコールバック */
  override function onCreate(board: MineSweeper): void {
    for(var w = 0; w < board.width; w++) {
      for(var h = 0; h < board.height; h++) {
        var el = this._createCellElement(w, h);
        if(h == 0){
            el.className += " head";
        }
        el.className += " cell";
        this.parent.appendChild(el);
      }
    }
  }
  
  /* マスが開かれた時のコールバック */
  override function onOpen(cell: Cell): void {
    var el = dom.id("cell-" + cell.x.toString() + "-" + cell.y.toString());
    el.className += " opened neighbor" + cell.getNeighbors().toString();
  }
  
  /* 地雷を開いた時のコールバック */
  override function onDead(cell: Cell): void {
    var el = dom.id("cell-" + cell.x.toString() + "-" + cell.y.toString());
    el.className += " bomb";
  }
  
  /* クリアした時のコールバック */
  override function onClear(duration: int): void {
    var message = this.game.width.toString() + "x" + this.game.height.toString();
    message += ",地雷率" + this.game.threshold.toString() + "のマインスイーパを";
    message += (duration/1000).toString() + "秒でクリアしました！";
    var createTweetButton = js.global["createTweetButton"] as function(:string): void;
    createTweetButton(message);
    dom.window.alert("クリアしました！ツイートボタンを押してみましょう。");
  }
  
  /* 1つのセル用の Element を生成する */
  function _createCellElement(x: int, y: int): Element {
    var el = dom.createElement("span");
    el.addEventListener("click", (e: Event) -> {
      this.game.open(x, y);
    });
    el.id = "cell-" + x.toString() + "-" + y.toString();
    return el;
  }
}