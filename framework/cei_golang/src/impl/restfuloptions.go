package impl

type RestfulOptions struct {
	Url               string
	ConnectionTimeout int
	ApiKey            string
	SecretKey         string
}

func (inst *RestfulOptions) SetFrom(option *RestfulOptions) {
	if option.Url != inst.Url {
		inst.Url = option.Url
	}
	if option.ConnectionTimeout != inst.ConnectionTimeout {
		inst.ConnectionTimeout = option.ConnectionTimeout
	}
	if option.ApiKey != inst.ApiKey {
		inst.ApiKey = option.ApiKey
	}
	if option.SecretKey != inst.SecretKey {
		inst.SecretKey = option.SecretKey
	}
}