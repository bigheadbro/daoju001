call mvn eclipse:eclipse -DdownloadSources=true
call mvn clean package -DskipTests=true 
@Pause