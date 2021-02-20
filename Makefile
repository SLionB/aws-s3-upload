all: target/aws-s3-upload-1.0.jar

target/aws-s3-upload-1.0.jar: src/main/java/aws/s3/upload/*.java
	mvn package

clean:
	mvn clean

