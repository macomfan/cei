# CEI
The full name of CEI is CryptoCurrency Exchange Interface. It is an SDK set supporting many CryptoCurrency exchanges and supporting many programing languages.

### Features
* Multi programing languages.
* Multi CryptoCurrency exchanges.
* Both Restful and WebSocket interface.
* XML configuration to define the exchange Interface.
* Json parser and builder configurable.
 
### Supported Programing Languages

|  Language   | Support Version |
|  ----  | ----  |
| [Java](https://github.com/macomfan/cei/tree/master/framework/cei_java) | v1.8 |
| [Python3](https://github.com/macomfan/cei)   | v3.8 |
| [golang](https://github.com/macomfan/cei)  | v1.13.5 (WebSocket is in developing)|
| C++ | Developing, not support yet |
| javascript | In plan, not support yet |
| C# | In plan, not support yet |

### Getting Started:
1. Clone from https://github.com/macomfan/cei.git
2. Create your main function and **import cn.ma.cei.generator.BuildSDK;**
3. Invoke **BuildSDK.initialize();** to initialize the builder for each type of languages
4. Invoke **BuildSDK.build(...)**; to build the SDK code. Example as below.
```java
    BuildSDK.build(
            "<The exchange config file folder>",
            "<The framework folder>",
            "<The output folder>",
            "java|golang|python3");
    // The <The output folder> is not used currently, all SDK code will be created at <The framework folder>.
    // Can use "*" instead of "java|golang|python3" to build all supported languages.
```

### Release History
[TBD]

### XML Definition
[TBD]
#### \<SDK>
The SDK is the root element in the CEI XML. It is used fo define the exchange name, and XML schema.
