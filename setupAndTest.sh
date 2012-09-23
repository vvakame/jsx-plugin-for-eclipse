git submodule init && \
git submodule update && \
\
mvn clean test install eclipse:clean eclipse:eclipse -DdownloadSources=true

