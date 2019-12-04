package com.philips.lighting.hue.sdk.wrapper.knowledgebase;

public class KnowledgeBaseImpl {
    private static native KnowledgeBase getKnowledgeBaseNative(int i);

    public static native String getSchemaVersion();

    public static native String getVersion();

    public static KnowledgeBase getKnowledgeBase(KnowledgeBaseType type) {
        if (type == null) {
            return null;
        }
        return getKnowledgeBaseNative(type.getValue());
    }
}
