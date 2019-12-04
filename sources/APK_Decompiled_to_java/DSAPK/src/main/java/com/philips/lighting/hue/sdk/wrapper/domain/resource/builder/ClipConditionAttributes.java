package com.philips.lighting.hue.sdk.wrapper.domain.resource.builder;

public class ClipConditionAttributes {
    protected static final String ConfigurationPath = "config/";
    protected static final String PresencePath = "presence/";
    protected static final String StatePath = "state/";

    public class Bridge {

        public class Configuration {
            public static final String LocalTime = "config/localtime";

            public Configuration() {
            }
        }

        public Bridge() {
        }
    }

    public class Group {

        public class Presence {

            public class State {
                public static final String LastUpdated = "presence/state/lastupdated";
                public static final String Presence = "presence/state/presence";
                public static final String PresenceAll = "presence/state/presence_all";

                public State() {
                }
            }

            public Presence() {
            }
        }

        public class State {
            public static final String AllOn = "state/all_on";
            public static final String AnyOn = "state/any_on";

            public State() {
            }
        }

        public Group() {
        }
    }

    public class Light {

        public class State {
            public static final String Brightness = "state/bri";
            public static final String CT = "state/ct";
            public static final String Hue = "state/hue";
            public static final String On = "state/on";
            public static final String Reachable = "state/reachable";
            public static final String Saturation = "state/sat";

            public State() {
            }
        }

        public Light() {
        }
    }

    public class Rule {
        public static final String LastTriggered = "lasttriggered";
        public static final String TimesTriggered = "timestriggered";

        public Rule() {
        }
    }

    public class Sensor {

        public class Configuration {
            public static final String Battery = "config/battery";
            public static final String MaximumSensitivity = "config/sensitivitymax";
            public static final String On = "config/on";
            public static final String Radius = "config/radius";
            public static final String Reachable = "config/reachable";
            public static final String Sensitivity = "config/sensitivity";
            public static final String SunriseOffset = "config/sunriseoffset";
            public static final String SunsetOffset = "config/sunsetoffset";
            public static final String ThresholdDark = "config/tholddark";
            public static final String ThresholdOffset = "config/tholdoffset";

            public Configuration() {
            }
        }

        public class State {
            public static final String ButtonEvent = "state/buttonevent";
            public static final String Dark = "state/dark";
            public static final String Daylight = "state/daylight";
            public static final String Flag = "state/flag";
            public static final String Humidity = "state/humidity";
            public static final String LastUpdated = "state/lastupdated";
            public static final String LightLevel = "state/lightlevel";
            public static final String Open = "state/open";
            public static final String Presence = "state/presence";
            public static final String Status = "state/status";
            public static final String Temperature = "state/temperature";

            public State() {
            }
        }

        public Sensor() {
        }
    }
}
