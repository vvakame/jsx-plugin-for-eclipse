import "test-case.jsx";

import "../jsx/model.jsx";

class _Test extends TestCase {
  function testInitializeBoard(): void {
    // 盤の初期化
    var board = new MineSweeper(new EventListener(), 3, 3, 0);

    this.expect(board._board.length).toBe(9);
  }

  function testClear(): void {
    // クリアの確認
    {
      var board = new MineSweeper(new EventListener(), 3, 3, 0);
      
      board.open(0, 0);
      this.expect(board.isCleared()).toBe(true); // 爆弾率0なので全部開いてクリア
    }
    {
      var board = new MineSweeper(new EventListener(), 3, 3, 0);
      this.modifyCell(board, 2, 2, true);
      
      board.open(0, 0);
      this.expect(board.isCleared()).toBe(true); // 一番右下の爆弾以外開くのでクリア
    }
  }

  function testHasLeft(): void {
    // 残りありの確認
    var board = new MineSweeper(new EventListener(), 3, 3, 0);
    this.modifyCell(board, 1, 2, true);
    this.modifyCell(board, 2, 1, true);
    
    board.open(0, 0);
    this.expect(board.isCleared()).toBe(false); // 右下空欄あり
  }

  function testDead(): void {
    // 失敗の確認
    var board = new MineSweeper(new EventListener(), 3, 3, 1);
    
    board.open(0, 0);
    this.expect(board.isCleared()).toBe(false); // 爆弾率1なのでどこ開いても死ぬ
  }

  function testEventListener(): void {
    // イベントリスナの確認
    var result = ""; // ここで初期化しておかないとエラーになる
    var listener = new SniffListener();
    listener.onCreateSniffer = (board) -> { result += "s"; };
    listener.onOpenSniffer = (cell) -> { result += "o"; };
    listener.onDeadSniffer = (cell) -> { result += "b"; };
    listener.onClearSniffer = (duration) -> { result += "e"; };

    {
      result = "";
      var board = new MineSweeper(listener, 3, 3, 0);
      this.modifyCell(board, 2, 2, true);
      
      board.open(0, 0);
      this.expect(result).toBe("sooooooooe"); // 開始+1ヶ所(8個)開く
    }
    {
      result = "";
      var board = new MineSweeper(listener, 3, 3, 0);
      this.modifyCell(board, 1, 2, true);
      this.modifyCell(board, 2, 1, true);
      
      board.open(0, 0);
      this.expect(result).toBe("soooo"); // 開始+1ヶ所(4個)開く
      board.open(0, 2);
      this.expect(result).toBe("sooooo"); // +1ヶ所(+1個)
      board.open(2, 0);
      this.expect(result).toBe("soooooo"); // +1ヶ所(+1個)
      board.open(1, 2);
      this.expect(result).toBe("soooooob"); // +死
    }
    {
      result = "";
      var board = new MineSweeper(listener, 3, 3, 0);
      this.modifyCell(board, 1, 2, true);
      this.modifyCell(board, 2, 1, true);
      
      board.open(0, 0);
      board.open(0, 2);
      board.open(2, 0);
      board.open(2, 2);
      this.expect(result).toBe("soooooooe"); // クリア
    }
  }

  function testClearTime(): void {
    // クリア秒数が正しく計測できること
    var result = -1;
    var listener = new SniffListener();
    listener.onClearSniffer = (duration) -> { result = duration; };

    var board = new MineSweeper(listener, 3, 3, 0);
    this.modifyCell(board, 2, 2, true);

    board.open(0, 0);
    this.expect(result).toBeGE(0); // 0より大きいこと
  }

  function modifyCell(board: MineSweeper, x: int, y: int, bomb: boolean): void {
    board.getCell(x, y).bomb = bomb;
  }
}

/*
 * 間に合わせの OnMineSweeperEventListener の実装。
 */
class EventListener implements OnMineSweeperEventListener {
  override function onCreate(board: MineSweeper): void {}
  override function onOpen(cell: Cell): void {}
  override function onDead(cell: Cell): void {}
  override function onClear(duration: int): void {}
}

/*
 * 各イベントの呼び出しにHookさせられるような OnMineSweeperEventListener の実装。
 * テスト中で匿名クラスとして実装が与えられればいいんだけど出来ないので。
 */
class SniffListener implements OnMineSweeperEventListener {
  var onCreateSniffer: (MineSweeper) -> void;
  var onOpenSniffer: (Cell) -> void;
  var onDeadSniffer: (Cell) -> void;
  var onClearSniffer: (int) -> void;

  override function onCreate(board: MineSweeper): void {
    if(this.onCreateSniffer) {
      this.onCreateSniffer(board);
    }
  }
  override function onOpen(cell: Cell): void {
    if(this.onOpenSniffer) {
      this.onOpenSniffer(cell);
    }
  }
  override function onDead(cell: Cell): void {
    if(this.onDeadSniffer) {
      this.onDeadSniffer(cell);
    }
  }
  override function onClear(duration: int): void {
    if(this.onClearSniffer) {
      this.onClearSniffer(duration);
    }
  }
}