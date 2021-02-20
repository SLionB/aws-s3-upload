package aws.s3.upload;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.transfer.MultipleFileUpload;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * Upload objects to an Amazon S3 bucket using S3 TransferManager.
 * 
 * This code expects that you have AWS credentials set up per:
 * http://docs.aws.amazon.com/java-sdk/latest/developer-guide/setup-credentials.html
 * Set credentials in the AWS credentials profile file on your local system, located at:
 *
 * ~/.aws/credentials on Linux, macOS, or Unix
 *
 * C:\Users\USERNAME\.aws\credentials on Windows
 *
 * This file should contain lines in the following format:
 *
 * [default]
 * aws_access_key_id = your_access_key_id
 * aws_secret_access_key = your_secret_access_key
 *
 * Set the AWS Region in the AWS config file on your local system, located at:
 *
 * ~/.aws/config on Linux, macOS, or Unix
 *
 * C:\Users\USERNAME\.aws\config on Windows
 *
 * This file should contain lines in the following format:
 *
 * [default]
 * region = eu-west-1
 *
 * Upload image
 *  AwsS3Upload "segmatix-upload/images" "localpath/idocs.png"
 *
 * View image
 * https://segmatix-upload.s3-eu-west-1.amazonaws.com/images/idocs.png
 */
public class AwsS3Upload {
    public static void uploadDir(String dir_path, String bucket_name,
                                 String key_prefix, boolean recursive, boolean pause) {
        System.out.println("directory: " + dir_path + (recursive ?
                " (recursive)" : "") + (pause ? " (pause)" : ""));

        TransferManager xfer_mgr = TransferManagerBuilder.standard().build();
        try {
            MultipleFileUpload xfer = xfer_mgr.uploadDirectory(bucket_name,
                    key_prefix, new File(dir_path), recursive);
            // loop with Transfer.isDone()
            AwsS3UploadProgress.showTransferProgress(xfer);
            // or block with Transfer.waitForCompletion()
            AwsS3UploadProgress.waitForCompletion(xfer);
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
        xfer_mgr.shutdownNow();
    }

    public static void uploadFileList(String[] file_paths, String bucket_name,
                                      String key_prefix, boolean pause) {
        System.out.println("file list: " + Arrays.toString(file_paths) +
                (pause ? " (pause)" : ""));
        // convert the file paths to a list of File objects
        ArrayList<File> files = new ArrayList<>();
        for (String path : file_paths) {
            files.add(new File(path));
        }

        TransferManager xfer_mgr = TransferManagerBuilder.standard().build();
        try {
            MultipleFileUpload xfer = xfer_mgr.uploadFileList(bucket_name,
                    key_prefix, new File("."), files);
            // loop with Transfer.isDone()
            AwsS3UploadProgress.showTransferProgress(xfer);
            // or block with Transfer.waitForCompletion()
            AwsS3UploadProgress.waitForCompletion(xfer);
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
        xfer_mgr.shutdownNow();
    }

    public static void uploadFile(String file_path, String bucket_name,
                                  String key_prefix, boolean pause) {
        System.out.println("file: " + file_path +
                (pause ? " (pause)" : ""));


        File f = new File(file_path);
        String file_name = f.getName();

        String key_name;
        if (key_prefix != null) {
            key_name = key_prefix + '/' + file_name;
        } else {
            key_name = file_name;
        }


        TransferManager xfer_mgr = TransferManagerBuilder.standard().build();
        try {
            Upload xfer = xfer_mgr.upload(bucket_name, key_name, f);
            // loop with Transfer.isDone()
            AwsS3UploadProgress.showTransferProgress(xfer);
            //  or block with Transfer.waitForCompletion()
            AwsS3UploadProgress.waitForCompletion(xfer);
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
        xfer_mgr.shutdownNow();
    }

}
