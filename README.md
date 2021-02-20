# aws-s3-upload
# Upload files to Amazon S3


## Upload objects to an Amazon S3 bucket using S3 TransferManager
 
This code expects that you have AWS credentials set up per:
http://docs.aws.amazon.com/java-sdk/latest/developer-guide/setup-credentials.html

### Set credentials in the AWS credentials profile file on your local system, located at:
~/.aws/credentials on Linux or C:\Users\USERNAME\.aws\credentials on Windows

This file should contain lines in the following format:
```
[default]
aws_access_key_id = your_access_key_id
aws_secret_access_key = your_secret_access_key
```

### Set the AWS Region in the AWS config file on your local system, located at:

~/.aws/config on Linux or C:\Users\USERNAME\.aws\config on Windows

This file should contain lines in the following format:
```
[default]
region = your_aws_region
```


## How to use

```
                "Usage:\n" +
                "    AwsS3Upload [--recursive] [--pause] <s3_path> <local_paths>" +
                "Where:" +
                "    --recursive - Only applied if local_path is a directory." +
                "                  Copies the contents of the directory recursively." +
                "    --pause     - Attempt to pause+resume the upload. This may not work for" +
                "                  small files." +
                "    s3_path     - The S3 destination (bucket/path) to upload the file(s) to." +
                "    local_paths - One or more local paths to upload to S3. These can be files" +
                "                  or directories. Globs are permitted (*.xml, etc.)" +
                "Examples:" +
                "    AwsS3Upload s3_path/image.png local_path/image.png" +
                "    AwsS3Upload s3_path local_path/image.png" +
                "    AwsS3Upload s3_path local_path/image*.png" +
                "    AwsS3Upload s3_path local_path";
                
```
