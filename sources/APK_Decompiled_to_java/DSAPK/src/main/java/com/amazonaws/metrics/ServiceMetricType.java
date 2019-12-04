package com.amazonaws.metrics;

public interface ServiceMetricType extends MetricType {
    public static final String DOWNLOAD_BYTE_COUNT_NAME_SUFFIX = "DownloadByteCount";
    public static final String DOWNLOAD_THROUGHPUT_NAME_SUFFIX = "DownloadThroughput";
    public static final String UPLOAD_BYTE_COUNT_NAME_SUFFIX = "UploadByteCount";
    public static final String UPLOAD_THROUGHPUT_NAME_SUFFIX = "UploadThroughput";

    String getServiceName();
}
