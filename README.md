InGrow Android Clients
======================

The InGrow Android Client enables customers to record data using InGrow from any Android application. The library supports event logging with variety of `non-array` properties. Events could have extra data as "Enrichment" like SESSION that is currently ready to use.

## Android Quick Start

Integrating InGrow with an Android application is pretty easy. Check out SAMPLE CORE that is available under "app" folder, just open the project in Android Studio.


### Gradle

```groovy
repositories {
    mavenCentral()
}
dependencies {
    implementation 'co.ingrow:'
}
```

### Initialize InGrow

To use InGrow in your Anroid application, it is needed to initialize InGrow library first. Of course Application file of you project(that extends Application and defined in Manifest by "name" attribute under application xml tag), but feel free to init it any other place in your app(but before using any other features of InGrow library). 

```java
InGrowClient.initialize(new InGrowClient.Builder(new InGrowProject("api-key", "project-name-or-number", "stream", /*isLoggingEnable*/true, /*anonymous_id*/"4692836429", /*user_id*/null), getApplication()).build());
```
it is possible to trace events related to specific user by defining `anonymous_id` in `InGrowClient.initialize(...)`
and you could define its USER_ID even after initialization by using:

```java
InGrowClient.enrichmentBySession(new InGrowSession("user_id"));
```
and you could trace user events even related to the before logging in activities.

### Sending events to InGrow

For sending events to InGrow feel free to create a `HashMap` of data. Keys of `HashMap` would be used as name of properties and values of it would be used as values of course.

```java
HashMap events = new HashMap<>();

events.put("name", "Nader");
events.put("position", "Developer");
events.put("isActive", true);
events.put("numberOfCommits", 12);

InGrowClient.client().logEvents(events);
```
### Debug mode
You might want to test logging events of your app and by this you could enable `debug mode` enable:

```java
InGrowClient.client().setDebugMode(true);
```
When debug mode is enabled, all exceptions will be thrown immediately, otherwise they will be logged and reported to
any callbacks, but never thrown.