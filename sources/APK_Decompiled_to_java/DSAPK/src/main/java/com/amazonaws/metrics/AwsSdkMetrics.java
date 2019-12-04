package com.amazonaws.metrics;

import com.amazonaws.SDKGlobalConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.metrics.MetricCollector.Factory;
import com.amazonaws.regions.Regions;
import com.amazonaws.util.AWSRequestMetrics.Field;
import com.amazonaws.util.AWSServiceMetrics;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.logging.LogFactory;

public enum AwsSdkMetrics {
    ;
    
    public static final String AWS_CREDENTAIL_PROPERTIES_FILE = "credentialFile";
    public static final String CLOUDWATCH_REGION = "cloudwatchRegion";
    private static final boolean DEFAULT_METRICS_ENABLED = false;
    private static final String DEFAULT_METRIC_COLLECTOR_FACTORY = "com.amazonaws.metrics.internal.cloudwatch.DefaultMetricCollectorFactory";
    public static final String DEFAULT_METRIC_NAMESPACE = "AWSSDK/Java";
    public static final String EXCLUDE_MACHINE_METRICS = "excludeMachineMetrics";
    public static final String HOST_METRIC_NAME = "hostMetricName";
    public static final String INCLUDE_PER_HOST_METRICS = "includePerHostMetrics";
    public static final String JVM_METRIC_NAME = "jvmMetricName";
    private static final String MBEAN_OBJECT_NAME = null;
    public static final String METRIC_NAME_SPACE = "metricNameSpace";
    public static final String METRIC_QUEUE_SIZE = "metricQueueSize";
    public static final String QUEUE_POLL_TIMEOUT_MILLI = "getQueuePollTimeoutMilli";
    private static final int QUEUE_POLL_TIMEOUT_MILLI_MINUMUM = 1000;
    private static final MetricRegistry REGISTRY = null;
    public static final String USE_SINGLE_METRIC_NAMESPACE = "useSingleMetricNamespace";
    private static volatile String credentialFile;
    private static volatile AWSCredentialsProvider credentialProvider;
    private static boolean dirtyEnabling;
    private static volatile String hostMetricName;
    private static volatile String jvmMetricName;
    private static volatile boolean machineMetricsExcluded;
    private static volatile MetricCollector mc;
    private static volatile String metricNameSpace;
    private static volatile Integer metricQueueSize;
    private static volatile boolean perHostMetricsIncluded;
    private static volatile Long queuePollTimeoutMilli;
    private static volatile Regions region;
    private static volatile boolean singleMetricNamespace;

    private static class MetricRegistry {
        private final Set<MetricType> metricTypes;
        private volatile Set<MetricType> readOnly;

        MetricRegistry() {
            this.metricTypes = new HashSet();
            this.metricTypes.add(Field.ClientExecuteTime);
            this.metricTypes.add(Field.Exception);
            this.metricTypes.add(Field.HttpClientRetryCount);
            this.metricTypes.add(Field.HttpRequestTime);
            this.metricTypes.add(Field.RequestCount);
            this.metricTypes.add(Field.RetryCount);
            this.metricTypes.add(Field.HttpClientSendRequestTime);
            this.metricTypes.add(Field.HttpClientReceiveResponseTime);
            this.metricTypes.add(Field.HttpClientPoolAvailableCount);
            this.metricTypes.add(Field.HttpClientPoolLeasedCount);
            this.metricTypes.add(Field.HttpClientPoolPendingCount);
            this.metricTypes.add(AWSServiceMetrics.HttpClientGetConnectionTime);
            syncReadOnly();
        }

        private void syncReadOnly() {
            this.readOnly = Collections.unmodifiableSet(new HashSet(this.metricTypes));
        }

        public boolean addMetricType(MetricType type) {
            boolean added;
            synchronized (this.metricTypes) {
                added = this.metricTypes.add(type);
                if (added) {
                    syncReadOnly();
                }
            }
            return added;
        }

        public <T extends MetricType> boolean addMetricTypes(Collection<T> types) {
            boolean added;
            synchronized (this.metricTypes) {
                added = this.metricTypes.addAll(types);
                if (added) {
                    syncReadOnly();
                }
            }
            return added;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:5:0x0009, code lost:
            if (r3.size() == 0) goto L_0x000b;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public <T extends com.amazonaws.metrics.MetricType> void setMetricTypes(java.util.Collection<T> r3) {
            /*
                r2 = this;
                java.util.Set<com.amazonaws.metrics.MetricType> r1 = r2.metricTypes
                monitor-enter(r1)
                if (r3 == 0) goto L_0x000b
                int r0 = r3.size()     // Catch:{ all -> 0x002b }
                if (r0 != 0) goto L_0x001b
            L_0x000b:
                java.util.Set<com.amazonaws.metrics.MetricType> r0 = r2.metricTypes     // Catch:{ all -> 0x002b }
                int r0 = r0.size()     // Catch:{ all -> 0x002b }
                if (r0 != 0) goto L_0x0015
                monitor-exit(r1)     // Catch:{ all -> 0x002b }
            L_0x0014:
                return
            L_0x0015:
                if (r3 != 0) goto L_0x001b
                java.util.List r3 = java.util.Collections.emptyList()     // Catch:{ all -> 0x002b }
            L_0x001b:
                java.util.Set<com.amazonaws.metrics.MetricType> r0 = r2.metricTypes     // Catch:{ all -> 0x002b }
                r0.clear()     // Catch:{ all -> 0x002b }
                boolean r0 = r2.addMetricTypes(r3)     // Catch:{ all -> 0x002b }
                if (r0 != 0) goto L_0x0029
                r2.syncReadOnly()     // Catch:{ all -> 0x002b }
            L_0x0029:
                monitor-exit(r1)     // Catch:{ all -> 0x002b }
                goto L_0x0014
            L_0x002b:
                r0 = move-exception
                monitor-exit(r1)     // Catch:{ all -> 0x002b }
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.amazonaws.metrics.AwsSdkMetrics.MetricRegistry.setMetricTypes(java.util.Collection):void");
        }

        public boolean removeMetricType(MetricType type) {
            boolean removed;
            synchronized (this.metricTypes) {
                removed = this.metricTypes.remove(type);
                if (removed) {
                    syncReadOnly();
                }
            }
            return removed;
        }

        public Set<MetricType> predefinedMetrics() {
            return this.readOnly;
        }
    }

    static {
        MBEAN_OBJECT_NAME = "com.amazonaws.management:type=" + AwsSdkMetrics.class.getSimpleName();
        metricNameSpace = DEFAULT_METRIC_NAMESPACE;
        String defaultMetrics = System.getProperty(SDKGlobalConfiguration.DEFAULT_METRICS_SYSTEM_PROPERTY);
        DEFAULT_METRICS_ENABLED = defaultMetrics != null;
        if (DEFAULT_METRICS_ENABLED) {
            boolean excludeMachineMetrics = false;
            boolean includePerHostMetrics = false;
            boolean useSingleMetricNamespace = false;
            for (String s : defaultMetrics.split(",")) {
                String part = s.trim();
                if (!excludeMachineMetrics && EXCLUDE_MACHINE_METRICS.equals(part)) {
                    excludeMachineMetrics = true;
                } else if (!includePerHostMetrics && INCLUDE_PER_HOST_METRICS.equals(part)) {
                    includePerHostMetrics = true;
                } else if (useSingleMetricNamespace || !USE_SINGLE_METRIC_NAMESPACE.equals(part)) {
                    String[] pair = part.split("=");
                    if (pair.length == 2) {
                        String key = pair[0].trim();
                        String value = pair[1].trim();
                        try {
                            if (AWS_CREDENTAIL_PROPERTIES_FILE.equals(key)) {
                                setCredentialFile0(value);
                            } else if (CLOUDWATCH_REGION.equals(key)) {
                                region = Regions.fromName(value);
                            } else if (METRIC_QUEUE_SIZE.equals(key)) {
                                Integer i = new Integer(value);
                                if (i.intValue() < 1) {
                                    throw new IllegalArgumentException("metricQueueSize must be at least 1");
                                }
                                metricQueueSize = i;
                            } else if (QUEUE_POLL_TIMEOUT_MILLI.equals(key)) {
                                Long i2 = new Long(value);
                                if (i2.intValue() < 1000) {
                                    throw new IllegalArgumentException("getQueuePollTimeoutMilli must be at least 1000");
                                }
                                queuePollTimeoutMilli = i2;
                            } else if (METRIC_NAME_SPACE.equals(key)) {
                                metricNameSpace = value;
                            } else if (JVM_METRIC_NAME.equals(key)) {
                                jvmMetricName = value;
                            } else if (HOST_METRIC_NAME.equals(key)) {
                                hostMetricName = value;
                            } else {
                                LogFactory.getLog(AwsSdkMetrics.class).debug("Ignoring unrecognized parameter: " + part);
                            }
                        } catch (Exception e) {
                            LogFactory.getLog(AwsSdkMetrics.class).debug("Ignoring failure", e);
                        }
                    } else {
                        continue;
                    }
                } else {
                    useSingleMetricNamespace = true;
                }
            }
            machineMetricsExcluded = excludeMachineMetrics;
            perHostMetricsIncluded = includePerHostMetrics;
            singleMetricNamespace = useSingleMetricNamespace;
        }
        REGISTRY = new MetricRegistry();
    }

    public static <T extends RequestMetricCollector> T getRequestMetricCollector() {
        if (mc == null && isDefaultMetricsEnabled()) {
            enableDefaultMetrics();
        }
        return mc == null ? RequestMetricCollector.NONE : mc.getRequestMetricCollector();
    }

    public static <T extends ServiceMetricCollector> T getServiceMetricCollector() {
        if (mc == null && isDefaultMetricsEnabled()) {
            enableDefaultMetrics();
        }
        return mc == null ? ServiceMetricCollector.NONE : mc.getServiceMetricCollector();
    }

    static MetricCollector getInternalMetricCollector() {
        return mc;
    }

    public static <T extends MetricCollector> T getMetricCollector() {
        if (mc == null && isDefaultMetricsEnabled()) {
            enableDefaultMetrics();
        }
        return mc == null ? MetricCollector.NONE : mc;
    }

    public static synchronized void setMetricCollector(MetricCollector mc2) {
        synchronized (AwsSdkMetrics.class) {
            MetricCollector old = mc;
            mc = mc2;
            if (old != null) {
                old.stop();
            }
        }
    }

    public static void setMachineMetricsExcluded(boolean excludeMachineMetrics) {
        machineMetricsExcluded = excludeMachineMetrics;
    }

    public static void setPerHostMetricsIncluded(boolean includePerHostMetrics) {
        perHostMetricsIncluded = includePerHostMetrics;
    }

    public static boolean isDefaultMetricsEnabled() {
        return DEFAULT_METRICS_ENABLED;
    }

    public static boolean isSingleMetricNamespace() {
        return singleMetricNamespace;
    }

    public static void setSingleMetricNamespace(boolean singleMetricNamespace2) {
        singleMetricNamespace = singleMetricNamespace2;
    }

    public static boolean isMetricsEnabled() {
        MetricCollector mc2 = mc;
        return mc2 != null && mc2.isEnabled();
    }

    public static boolean isMachineMetricExcluded() {
        return machineMetricsExcluded;
    }

    public static boolean isPerHostMetricIncluded() {
        return perHostMetricsIncluded;
    }

    public static boolean isPerHostMetricEnabled() {
        if (perHostMetricsIncluded) {
            return true;
        }
        String host = hostMetricName;
        if ((host == null ? "" : host.trim()).length() <= 0) {
            return false;
        }
        return true;
    }

    public static synchronized boolean enableDefaultMetrics() {
        boolean z = false;
        synchronized (AwsSdkMetrics.class) {
            if (mc == null || !mc.isEnabled()) {
                if (dirtyEnabling) {
                    throw new IllegalStateException("Reentrancy is not allowed");
                }
                dirtyEnabling = true;
                try {
                    MetricCollector instance = ((Factory) Class.forName(DEFAULT_METRIC_COLLECTOR_FACTORY).newInstance()).getInstance();
                    if (instance != null) {
                        setMetricCollector(instance);
                        dirtyEnabling = false;
                        z = true;
                    } else {
                        dirtyEnabling = false;
                    }
                } catch (Exception e) {
                    LogFactory.getLog(AwsSdkMetrics.class).warn("Failed to enable the default metrics", e);
                    dirtyEnabling = false;
                } catch (Throwable th) {
                    dirtyEnabling = false;
                    throw th;
                }
            }
        }
        return z;
    }

    public static void disableMetrics() {
        setMetricCollector(MetricCollector.NONE);
    }

    public static boolean add(MetricType type) {
        if (type == null) {
            return false;
        }
        return REGISTRY.addMetricType(type);
    }

    public static <T extends MetricType> boolean addAll(Collection<T> types) {
        if (types == null || types.size() == 0) {
            return false;
        }
        return REGISTRY.addMetricTypes(types);
    }

    public static <T extends MetricType> void set(Collection<T> types) {
        REGISTRY.setMetricTypes(types);
    }

    public static boolean remove(MetricType type) {
        if (type == null) {
            return false;
        }
        return REGISTRY.removeMetricType(type);
    }

    public static Set<MetricType> getPredefinedMetrics() {
        return REGISTRY.predefinedMetrics();
    }

    public static AWSCredentialsProvider getCredentialProvider() {
        StackTraceElement[] e = Thread.currentThread().getStackTrace();
        for (StackTraceElement className : e) {
            if (className.getClassName().equals(DEFAULT_METRIC_COLLECTOR_FACTORY)) {
                return credentialProvider;
            }
        }
        SecurityException ex = new SecurityException();
        LogFactory.getLog(AwsSdkMetrics.class).warn("Illegal attempt to access the credential provider", ex);
        throw ex;
    }

    public static synchronized void setCredentialProvider(AWSCredentialsProvider provider) {
        synchronized (AwsSdkMetrics.class) {
            credentialProvider = provider;
        }
    }

    public static Regions getRegion() {
        return region;
    }

    public static void setRegion(Regions region2) {
        region = region2;
    }

    public static String getCredentailFile() {
        return credentialFile;
    }

    public static void setCredentialFile(String filepath) throws IOException {
        setCredentialFile0(filepath);
    }

    private static void setCredentialFile0(String filepath) throws IOException {
        final PropertiesCredentials cred = new PropertiesCredentials(new File(filepath));
        synchronized (AwsSdkMetrics.class) {
            credentialProvider = new AWSCredentialsProvider() {
                public void refresh() {
                }

                public AWSCredentials getCredentials() {
                    return cred;
                }
            };
            credentialFile = filepath;
        }
    }

    public static Integer getMetricQueueSize() {
        return metricQueueSize;
    }

    public static void setMetricQueueSize(Integer size) {
        metricQueueSize = size;
    }

    public static Long getQueuePollTimeoutMilli() {
        return queuePollTimeoutMilli;
    }

    public static void setQueuePollTimeoutMilli(Long timeoutMilli) {
        queuePollTimeoutMilli = timeoutMilli;
    }

    public static String getMetricNameSpace() {
        return metricNameSpace;
    }

    public static void setMetricNameSpace(String metricNameSpace2) {
        if (metricNameSpace2 == null || metricNameSpace2.trim().length() == 0) {
            throw new IllegalArgumentException();
        }
        metricNameSpace = metricNameSpace2;
    }

    public static String getJvmMetricName() {
        return jvmMetricName;
    }

    public static void setJvmMetricName(String jvmMetricName2) {
        jvmMetricName = jvmMetricName2;
    }

    public static String getHostMetricName() {
        return hostMetricName;
    }

    public static void setHostMetricName(String hostMetricName2) {
        hostMetricName = hostMetricName2;
    }
}
