import "js/web.jsx";
import "view.jsx";

// check for returnStatement
class _Main {
  static function main(args: string[]): void {
  }
  
  static function hoge(): string {
    return "hoge";
  }
  
  static function fuga(): void {
    return;
  }
}
