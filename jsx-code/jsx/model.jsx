/* MineSweeper で発生したイベントのコールバックに使う。 */
interface OnMineSweeperEventListener {
  /* ステージが生成された時 */
  function onCreate(board: MineSweeper): void;
  /* 通常のセルが開かれた時。地雷セルの時は呼ばれない。 */
  function onOpen(cell: Cell): void;
  /* 地雷セルが開かれた時。通常セルの時は呼ばれない。 */
  function onDead(cell: Cell): void;
  /* ゲームクリアした時。 */
  function onClear(duration: int): void;
}

/* 盤面のセル1個を表す。 */
class Cell {
  var x: int;
  var y: int;
  var bomb: boolean;
  var _board: MineSweeper;
  var hoge;
  
  var _opened = false;
  
  /* コンストラクタ。4つのフィールドに渡された値を初期値としてセットする。 */
  function constructor(board: MineSweeper, x: int, y: int, bomb: boolean) {
    this._board = board;
    this.x = x;
    this.y = y;
    this.bomb = bomb;
  }
  
  /* 周囲8セルに爆弾がいくつあるかを返す */
  function getNeighbors(): int {
    return this._board.getNeighborsBomb(this.x, this.y);
  }
  
  /* このセルが既に開かれているかを返す */
  function isOpened(): boolean {
    return this._opened;
  }
}

/* マインスイーパのゲーム制御のクラス。描画は行わない。 */
class MineSweeper {
  /* 盤の横幅 */
  var width: int;
  /* 盤の縦幅 */
  var height: int;
  /* 地雷の割合 */
  var threshold: number;
  
  /* 盤面の状態 */
  var _board: Array.<Cell> = new Array.<Cell>();
  var _start: Date;
  
  /* イベント通知先 */
  var _listener: OnMineSweeperEventListener;
  
  /* コンストラクタ。初期の盤面の生成を行う。*/
  function constructor(listener: OnMineSweeperEventListener) {
    this._init(listener, 10, 10, 0.15);
  }
  
  /* コンストラクタ。初期の盤面の生成を行う。*/
  function constructor(listener: OnMineSweeperEventListener, width: int, height: int, threshold: number) {
    this._init(listener, width, height, threshold);
  }
  
  function _init(listener: OnMineSweeperEventListener, width: int, height: int, threshold: number): void {
    this._listener = listener;
    this.width = width;
    this.height = height;
    this.threshold = threshold;
    
    for(var w = 0; w < this.width; w++) {
      for(var h = 0; h < this.height; h++) {
        var cell = new Cell(this, w, h, Math.random() < this.threshold);
        this._board.push(cell);
      }
    }
    
    this._start = new Date();
    this._listener.onCreate(this);
  }
  
  /* 指定箇所のセルを取得する。 */
  function getCell(x: int, y: int): Cell {
    var cells: Array.<Cell> = this._board.filter((cell) -> { return cell.x == x && cell.y == y;});
    return cells[0];
  }
  
  /* 指定箇所の周囲8セルを取得する。 */
  function getNeighbors(x: int, y: int): Array.<Cell> {
    var neighbors = this._board.filter((c) -> {
      return (c.x == x && c.y == y) == false && (x - 1 <= c.x && c.x <= x + 1) && (y - 1 <= c.y && c.y <= y + 1);
    });
    return neighbors;
  }
  
  /**
   * 指定箇所のセルを開く。再帰的に開ける場合はそこも開く。
   * onClearが複数回呼ばれると都合が悪いので _openについて [root] がtrueかどうかで呼ぶかどうかを判断する。
   */
  function open(x: int, y: int): void {
    this._open(x, y, true);
  }
  
  function _open(x: int, y: int, root: boolean): void {
    var cell = this.getCell(x, y);
    if(cell.isOpened()) {
      return;
    }
    cell._opened = true;
    if(cell.bomb) {
      this._listener.onDead(cell);
    } else {
      this._listener.onOpen(cell);
      // 周囲8マス全てが地雷じゃない場合再帰的に開いていく
      if(cell.getNeighbors() == 0) {
        // 周囲8マスのセルを開ける
        this.getNeighbors(x, y).forEach((c) -> {
          this._open(c.x, c.y, false);
        });
      }
    }
    if(root && this.isCleared()) {
      this._listener.onClear(new Date().getTime() - this._start.getTime());
    }
  }
  
  /* 指定箇所が爆弾かどうかを調べる。 */
  function isBomb(x: int, y: int): boolean {
    return this.getCell(x, y).bomb;
  }
  
  /* クリア済の状態かどうかを調べる。 */
  function isCleared(): boolean {
    // 閉じてる非地雷セルがあったら終わってない
    var closedCells = this._board.filter((c) -> {
      return c.isOpened() == false && c.bomb == false;
    });
    var closedCellExists = closedCells.length != 0;
    
    // 開いてる地雷セルがあったらクリアではない
    var openedBombs = this._board.filter((c) -> {
      return c.isOpened() && c.bomb;
    });
    var openedBombsExists = openedBombs.length != 0;
    
    return closedCellExists == false && openedBombsExists == false;
  }
  
  /* 指定箇所の周囲に爆弾がいくつあるかを調べる。 */
  function getNeighborsBomb(x: int, y: int): int {
    var bomb = 0;
    this.getNeighbors(x, y).forEach((c) -> {
      bomb += c.bomb ? 1 : 0;
    });
    return bomb;
  }
}
