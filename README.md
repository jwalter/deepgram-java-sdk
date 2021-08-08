# Deepgram Java/Kotlin SDK

[![Contributor Covenant](https://img.shields.io/badge/Contributor%20Covenant-v2.0%20adopted-ff69b4.svg?style=flat-rounded)](CODE_OF_CONDUCT.md)

Reactive work-in-progress Java and Kotlin SDK for [Deepgram](https://www.deepgram.com)'s automated
speech recognition APIs.

To access the API you will need a Deepgram account. Sign up for free at
[signup][signup].

You can learn more about the full Deepgram API at [https://developers.deepgram.com](https://developers.deepgram.com).

## Installation

### With Maven

```XML
<dependency>
    <groupId>com.waltersson.deepgram</groupId>
    <artifactId>deepgram-sdk</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

### With Gradle

```Kotlin
implementation("com.waltersson.deepgram", "deepgram-sdk", "0.0.1-SNAPSHOT")
```

## Constructor

```Java
import com.waltersson.deepgram.sdk.Deepgram;

public class JavaApp {
    public static void main(String[] args) {
        var deepgram = new Deepgram("YOUR_API_KEY", "CUSTOM_API_URL");
    }
}
```

## Usage

## Project Management

### List Projects

Retrieve all projects

```Java
Mono<Projects> projects = deepgram.projects().list();
```

### Get a Project

Retrieves a project based on the provided project id.

```Java
var project = deepgram.projects().get(projectId);
```

### Update a Project

Updates a project.

```Java
var updateResponse = deepgram.projects().update(project);
```

## Key Management

### List Keys

Retrieves all keys for a given project.

```Java
var response = deepgram.keys().list(projectId);
```

### Create Key

Create a new API key for a project with a name for the key.

```Java
var response = deepgram.keys().create(projectId, "COMMENT_FOR_KEY");
```

### Delete key

Delete an existing API key using the `keys.delete` method with the key to
delete.

```Java
deepgram.keys().delete(projectId, "KEY_ID");
```

## Usage

### Requests by Project

Retrieves transcription requests for a project based on the provided options.

```Java
var response1 = deepgram.usage().listRequests(PROJECT_ID, "START_DATE");
var response2 = deepgram.usage().listRequests(PROJECT_ID, "START_DATE", "END_DATE");
var response3 = deepgram.usage().listRequests(PROJECT_ID, "START_DATE", "END_DATE", "PAGE");
var response4 = deepgram.usage().listRequests(PROJECT_ID, "START_DATE", "END_DATE", "PAGE", "LIMIT");
```

### Get Specific Request

Retrieves a specific transcription request for a project based on the provided
`projectId` and `requestId`.

```Java
var response = deepgram.usage().getRequest(PROJECT_ID, REQUEST_ID);
```

### Get Usage by Project

Retrieves aggregated usage data for a project based on the provided options.

```Java
var response1 = deepgram.usage().getSummary(PROJECT_ID, "2020-01-01T00:00:00+00:00");
var respons2 = deepgram.usage().getSummary(PROJECT_ID, "2020-01-01T00:00:00+00:00", "2021-01-01T00:00:00+00:00");
// There are several more overloads
```

### Get Fields

Retrieves features used by the provided projectId based on the provided options.

```Java
var response = deepgram.usage().getUsage(PROJECT_ID, "2020-01-01T00:00:00+00:00");
// There are several more overloads
```

## Further Reading

Check out the Developer Documentation at [https://developers.deepgram.com/](https://developers.deepgram.com/)

[signup]: https://console.deepgram.com?utm_source=node-sdk&utm_content=readme