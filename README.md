# Event logging For Java: El4j

## How to "use" -- Setup instructions:

To add to your project (gradle):

### Local setup

```groovy

repositories { 
    mavenLocal()
}

dependencies {
    implementation 'org.el4j.observability:el4j-observability:0.0.1-SNAPSHOT'
}

```
### Add to your application yaml file

```yaml

logging.event.enabled: true # default value true

```
### Usage in more details:

```yaml
elf4j.metrics.adminRole: admin
elf4j.metrics.adminPageName: adminPage
elf4j.metrics.logging.ttl: 60 # seconds, default 604800s

elf4j.metrics.logging.extra.monitoring.enabled: true # default false
elf4j.metrics.logging.extra.monitoring.ttl: 60 # seconds, default 86400s
elf4j.metrics.logging.extra.packages:
  -
    name: com.experiment.utils
    level: ALL
  -
    name: com.experiment.internal
    level: ERROR

```

### Other usage example:

```properties
elf4j.metrics.adminRole=admin
elf4j.metrics.adminPageName=adminPage
elf4j.metrics.logging.ttl=60 # seconds, default 604800s

elf4j.metrics.logging.extra.monitoring.enabled=true # default false
elf4j.metrics.logging.extra.monitoring.ttl=60 # seconds, default 86400s

elf4j.metrics.logging.extra.packages[0]=com.experiment.utils,ALL
elf4j.metrics.logging.extra.packages[1]=com.experiment.internal,ERROR
```

### UI metrics collection:

Add the following to your html file.

```html
<script>
    const observer = new PerformanceObserver((list) => {
        const entries = list.getEntries();
        const lastEntry = entries[entries.length - 1]; // Use the latest LCP candidate
        const time = lastEntry.startTime;//startTime;
        const pathname = window.location.pathname;
        window.fetch(`/observer/v1/ui/performance?pathname=${pathname}&time=${time}`);
    });
    window.addEventListener("load", () => observer.observe({type: "largest-contentful-paint", buffered: true}));
</script>
```