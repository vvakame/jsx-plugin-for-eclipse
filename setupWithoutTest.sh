git submodule init && \
git submodule update && \
\
mvn -f jsx-peg-syntax/pom.xml clean test install eclipse:clean eclipse:eclipse -DdownloadSources=true -Dmaven.test.skip=true && \
mvn -f jsx-wrapper/pom.xml clean test install eclipse:clean eclipse:eclipse -DdownloadSources=true -Dmaven.test.skip=true && \
mvn -f net.vvakame.jsx.eclipse.plugin.core/pom.xml clean test install eclipse:clean eclipse:eclipse -DdownloadSources=true -Dmaven.test.skip=true && \
mvn -f net.vvakame.jsx.eclipse.plugin.core.tests/pom.xml clean test install eclipse:clean eclipse:eclipse -DdownloadSources=true -Dmaven.test.skip=true && \
mvn -f net.vvakame.jsx.eclipse.feature/pom.xml clean test install eclipse:clean eclipse:eclipse -DdownloadSources=true -Dmaven.test.skip=true && \
mvn -f net.vvakame.jsx.eclipse.updatesite/pom.xml clean test install eclipse:clean eclipse:eclipse -DdownloadSources=true -Dmaven.test.skip=true

