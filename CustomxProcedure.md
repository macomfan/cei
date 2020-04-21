### Custom Procedure
CEI supports to define the custom procedure to process the data.

```xml
<custom_procedures>
    <function name="xxx">
        <inputs>
            <string name="xxx"/>
            <int name="xxx"/>
        </inputs>
        <return name="{xxx}"/>
        <procedure>
            <get_now name="{xxx}"/>
            <json_builder name="{xxx}"/>
        </procedure>
    </function>
</custom_procedures>
```

To invoke
```xml
<procedure>
    <invoke name="xxx" inputs="{xxx},{xxx}" return="{xxx}"/>
</procedure>
```

#### Get Now
```xml
<get_now name="{xxx}" format="Unix_ms"/>
```

#### Base64
```xml
<base64 name="{xxx}" input="{xxx}"/>
```

#### HMAC SHA256
```xml
<hmacsha256 name="{xxx}" input="{xxx}" key="{xxx}"/>
```

#### URL Escape
```xml
<url_escape name="{xxx}" input="{xxx}"/>
```

#### Gzip
```xml
<gzip name="{xxx}" input="{xxx}" type="decompress"/>
```

#### Add Query String
```xml
<add_query_string input="{xxx}" key="xxx" value="{xxx}"/>
```

#### Get Request Body
```xml
<TBD/>
```


#### Add Header
```xml
<TBD/>
```

#### Combine Query String
```xml
<combine_query_string name="{xxx}" sort="asc" separator="xxx"/>
```

#### Get Request Information
```xml
<get_request_info name="{xxx}" info="method" convert="uppercase"/>
```

#### String builder
```xml
<string_builder name="{buffer}"/>
```

#### Json Parser/Builder
Refer to Json Parser/Builder

