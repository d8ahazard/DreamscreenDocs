package com.philips.lighting.hue.sdk.wrapper.discovery;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nonnull;

public interface BridgeDiscovery {

    public interface Callback {
        void onFinished(@Nonnull List<BridgeDiscoveryResult> list, @Nonnull ReturnCode returnCode);
    }

    public enum Option {
        UPNP,
        IPSCAN,
        NUPNP;
        
        @Deprecated
        public static final int ALL = 0;
        @Deprecated
        public static final int NUPNP_AND_IPSCAN = 0;
        @Deprecated
        public static final int UPNP_AND_IPSCAN = 0;
        @Deprecated
        public static final int UPNP_AND_NUPNP = 0;
        public final int flag;

        static {
            UPNP_AND_NUPNP = UPNP.flag | NUPNP.flag;
            UPNP_AND_IPSCAN = UPNP.flag | IPSCAN.flag;
            NUPNP_AND_IPSCAN = NUPNP.flag | IPSCAN.flag;
            ALL = UPNP.flag | NUPNP.flag | IPSCAN.flag;
        }

        static long valueFromEnumSet(@Nonnull EnumSet<Option> e) {
            long value = 0;
            Iterator it = e.iterator();
            while (it.hasNext()) {
                value |= (long) ((Option) it.next()).flag;
            }
            return value;
        }

        @Nonnull
        static EnumSet<Option> enumSetFromValue(long value) {
            EnumSet<Option> set = EnumSet.noneOf(Option.class);
            Iterator it = EnumSet.allOf(Option.class).iterator();
            while (it.hasNext()) {
                Option e = (Option) it.next();
                if ((((long) e.flag) & value) != 0) {
                    set.add(e);
                }
            }
            return set;
        }
    }

    public enum ReturnCode {
        SUCCESS(0),
        BUSY(-5),
        NULL_PARAMETER(-101),
        STOPPED(-303),
        MISSING_DISCOVERY_METHODS(-401);
        
        private final int value;

        private ReturnCode(int v) {
            this.value = v;
        }

        public int getValue() {
            return this.value;
        }

        public static ReturnCode fromValue(int val) {
            ReturnCode[] values;
            for (ReturnCode v : values()) {
                if (v.getValue() == val) {
                    return v;
                }
            }
            return null;
        }
    }

    boolean isSearching();

    @Deprecated
    void search(int i, Callback callback);

    void search(Callback callback);

    @Deprecated
    void search(Option option, Callback callback);

    void search(@Nonnull EnumSet<Option> enumSet, Callback callback);

    void stop();
}
