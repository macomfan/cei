package restful

type RestfulOptions struct {
	URL               string
	ConnectionTimeout int
	ApiKey            string
	SecretKey         string
}

func (inst *RestfulOptions) SetFrom(option *RestfulOptions) {
	if option.URL != nil {
		inst.URL = option.URL
	}
	if option.ConnectionTimeout != nil {
		inst.ConnectionTimeout = option.ConnectionTimeout
	}
	if option.ApiKey != nil {
		inst.ApiKey = option.ApiKey
	}
	if option.SecretKey != nil {
		inst.SecretKey = option.SecretKey
	}
}