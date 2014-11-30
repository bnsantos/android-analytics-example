Android Analytics
========================

Simple example App with three events tracked LOGGED, PROJECT, NOTE and COMMENT using one of these three analytics tools:

[MixPanel](https://mixpanel.com/)

[Flurry](http://www.flurry.com/)

[Countly](https://count.ly/)


To use it you should replace the API keys on Constansts.java file
```java
private static String MIXPANEL_TOKEN = ""; //Set your default token here
private static String FLURRY_TOKEN = "";  //Your flurry token
private static String COUNTLY_SERVER = ""; //Your countly server here
private static String COUNTLY_APP_KEY = ""; //Your countly app key here
```
Or you can add those keys while running the app in settings.
