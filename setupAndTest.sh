git submodule init && \
git submodule update && \
\
mvn -f jsx-peg-syntax/pom.xml clean test install eclipse:clean eclipse:eclipse -DdownloadSources=true && \
mvn -f jsx-wrapper/pom.xml clean test install eclipse:clean eclipse:eclipse -DdownloadSources=true && \
mvn -f editor/pom.xml clean test install eclipse:clean eclipse:eclipse -DdownloadSources=true && \
mvn -f editor.feature/pom.xml clean test install eclipse:clean eclipse:eclipse -DdownloadSources=true && \
mvn -f editor.test/pom.xml clean test install eclipse:clean eclipse:eclipse -DdownloadSources=true

