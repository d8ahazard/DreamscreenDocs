package com.amazonaws;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.event.ProgressListener;
import com.amazonaws.metrics.RequestMetricCollector;

public abstract class AmazonWebServiceRequest implements Cloneable {
    private AmazonWebServiceRequest cloneSource;
    private AWSCredentials credentials;
    private ProgressListener generalProgressListener;
    private final RequestClientOptions requestClientOptions = new RequestClientOptions();
    @Deprecated
    private RequestMetricCollector requestMetricCollector;

    public void setRequestCredentials(AWSCredentials credentials2) {
        this.credentials = credentials2;
    }

    public AWSCredentials getRequestCredentials() {
        return this.credentials;
    }

    public RequestClientOptions getRequestClientOptions() {
        return this.requestClientOptions;
    }

    @Deprecated
    public RequestMetricCollector getRequestMetricCollector() {
        return this.requestMetricCollector;
    }

    @Deprecated
    public void setRequestMetricCollector(RequestMetricCollector requestMetricCollector2) {
        this.requestMetricCollector = requestMetricCollector2;
    }

    @Deprecated
    public <T extends AmazonWebServiceRequest> T withRequestMetricCollector(RequestMetricCollector metricCollector) {
        setRequestMetricCollector(metricCollector);
        return this;
    }

    public void setGeneralProgressListener(ProgressListener generalProgressListener2) {
        this.generalProgressListener = generalProgressListener2;
    }

    public ProgressListener getGeneralProgressListener() {
        return this.generalProgressListener;
    }

    public <T extends AmazonWebServiceRequest> T withGeneralProgressListener(ProgressListener generalProgressListener2) {
        setGeneralProgressListener(generalProgressListener2);
        return this;
    }

    /* access modifiers changed from: protected */
    public final <T extends AmazonWebServiceRequest> T copyBaseTo(T target) {
        target.setGeneralProgressListener(this.generalProgressListener);
        target.setRequestMetricCollector(this.requestMetricCollector);
        return target;
    }

    public AmazonWebServiceRequest getCloneSource() {
        return this.cloneSource;
    }

    public AmazonWebServiceRequest getCloneRoot() {
        AmazonWebServiceRequest cloneRoot = this.cloneSource;
        if (cloneRoot != null) {
            while (cloneRoot.getCloneSource() != null) {
                cloneRoot = cloneRoot.getCloneSource();
            }
        }
        return cloneRoot;
    }

    private void setCloneSource(AmazonWebServiceRequest cloneSource2) {
        this.cloneSource = cloneSource2;
    }

    public AmazonWebServiceRequest clone() {
        try {
            AmazonWebServiceRequest cloned = (AmazonWebServiceRequest) super.clone();
            cloned.setCloneSource(this);
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException("Got a CloneNotSupportedException from Object.clone() even though we're Cloneable!", e);
        }
    }
}
