git submodule init && \
git submodule update && \
\
mvn -f jsx-peg-syntax/pom.xml clean test install eclipse:clean eclipse:eclipse -DdownloadSources=true && \
mvn -f jsx-wrapper/pom.xml clean test install eclipse:clean eclipse:eclipse -DdownloadSources=true && \
mvn -f jsx.eclipse.plugin.core/pom.xml clean test install eclipse:clean eclipse:eclipse -DdownloadSources=true && \
mvn -f jsx.eclipse.plugin.core.tests/pom.xml clean test install eclipse:clean eclipse:eclipse -DdownloadSources=true && \
mvn -f jsx.eclipse.plugin.feature/pom.xml clean test install eclipse:clean eclipse:eclipse -DdownloadSources=true && \
mvn -f jsx.eclipse.plugin.updatesite/pom.xml clean test install eclipse:clean eclipse:eclipse -DdownloadSources=true

