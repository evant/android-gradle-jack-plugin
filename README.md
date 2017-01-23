# android-gradle-jack-plugin
Fork of the android gradle plugin that supports jack plugins

based on version 2.2.0

See http://tools.android.com/build/gradleplugin for building and installing.

```groovy
android {
  defaultConfig {
    jackOptions {
      enabled true
      pluginPath files('path-to-plugin.jar')
      plugin 'com.exmaple.PluginName'
    }
  }
}
```
