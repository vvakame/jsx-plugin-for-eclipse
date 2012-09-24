# JSX editor for Eclipse の使い方

この説明を書いた時のバージョン v0.2.0

## インストールします

EclipseのInstall New Softwareで `http://vvakame.github.com/jsx-plugin-for-eclipse/site/` をupdate siteとして指定してインストールします。

## 設定します

入れたそのままじゃ使えないうえに、設定が済んでなくても警告を表示してくれたりはしないのです。甘えた奴から死んでいくんだ。

Eclipse全体の設定(Macの場合 Cmd+, で出る)に、JsxEditor Preference という項目があるので、JSXとnode.jsのパスを設定します。作者の場合、Mac OS X + MacPortsユーザなので JSXのパスには `~/work/JSX/bin/jsx`、node.jsのパスには`/opt/local/bin/node` とかって指定されています。

## 使える機能

[ここ](https://github.com/vvakame/jsx-plugin-for-eclipse/blob/master/changelog.txt) に書いてある通り。

## できないこと

* コンパイルエラーの表示
* コンパイルの実行
* コードフォーマット
* 各種リファクタリング
* その他思いつかないけどできないこといっぱい

## その他

### 開発に参加したい

マジで？ @vvakame か @u1aryz 宛てに連絡ください。pull requestくれてもいいです。

### 欲しい機能がある

Issue に書いてください。できればpull requestもください。

### 機能ショボいな(鼻

もしEclipse Plugin開発について豊富な知識を持っていたら、聞きたい事発生した時に相談したいのでTwitter IDとか教えてください。
