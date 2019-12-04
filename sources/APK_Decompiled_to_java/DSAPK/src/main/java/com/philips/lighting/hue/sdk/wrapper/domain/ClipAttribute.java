package com.philips.lighting.hue.sdk.wrapper.domain;

public class ClipAttribute {

    public class Bridge {

        public class Configuration {
            public static final String LocalTime = "localtime";

            public Configuration() {
            }
        }

        public Bridge() {
        }
    }

    public class Group {

        public class Presence {

            public class State {
                public static final String LastUpdated = "lastupdated";
                public static final String Presence = "presence";
                public static final String PresenceAll = "presence_all";

                public State() {
                }
            }

            public Presence() {
            }
        }

        public class State {
            public static final String AllOn = "all_on";
            public static final String AnyOn = "any_on";

            public State() {
            }
        }

        public Group() {
        }
    }

    public class Light {

        public class State {
            public static final String Brightness = "bri";
            public static final String CT = "ct";
            public static final String Hue = "hue";
            public static final String On = "on";
            public static final String Reachable = "reachable";
            public static final String Saturation = "sat";

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
            public static final String Alert = "alert";
            public static final String Battery = "battery";
            public static final String Device = "device";
            public static final String Latitude = "latitude";
            public static final String LedIndication = "ledindication";
            public static final String Longitude = "longitude";
            public static final String MaximumSensitivity = "sensitivitymax";
            public static final String On = "on";
            public static final String Radius = "radius";
            public static final String Reachable = "reachable";
            public static final String Sensitivity = "sensitivity";
            public static final String SunriseOffset = "sunriseoffset";
            public static final String SunsetOffset = "sunsetoffset";
            public static final String ThresholdDark = "tholddark";
            public static final String ThresholdOffset = "tholdoffset";
            public static final String UserTest = "usertest";

            public Configuration() {
            }
        }

        public class State {
            public static final String ButtonEvent = "buttonevent";
            public static final String Dark = "dark";
            public static final String Daylight = "daylight";
            public static final String Flag = "flag";
            public static final String Humidity = "humidity";
            public static final String LastUpdated = "lastupdated";
            public static final String LightLevel = "lightlevel";
            public static final String Open = "open";
            public static final String Presence = "presence";
            public static final String Status = "status";
            public static final String Temperature = "temperature";

            public State() {
            }
        }

        public Sensor() {
        }
    }
}
