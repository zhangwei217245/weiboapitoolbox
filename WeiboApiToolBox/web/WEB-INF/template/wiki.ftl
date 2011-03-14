
== ${spec.resourcePath} ==
${spec.vc2shortdesc}
${spec.vc2maindesc}
== URL ==
<span style="text-transform:lowercase;font-weight:600">${spec.numbaseurlid.vc2baseurl}/${spec.resourcePath}.(json|xml)</span>
== 支持格式 ==
<span style="text-transform:uppercase;font-weight:600">{{{format}}}</span>
== HTTP请求方式 ==
<span style="text-transform:uppercase;font-weight:600">{{{httpMethod}}}</span>
== 是否需要登录 ==
<span style="text-transform:lowercase;font-weight:600">{{{needAuth}}}</span><br/>
关于授权机制，参见[[授权机制说明|授权机制声明]]
== 请求数限制 ==
<span style="text-transform:lowercase;font-weight:600">{{{rateLimit}}}</span><br/>
关于请求数限制，参见[[Rate-limiting|接口访问权限说明]]
== 请求参数 ==
{| border="1" cellspacing="0" cellpadding="0" width="100%" class="parameters" style="border-color: #CCCCCC;"
|-
!width="10%" style="text-align:center;font-weight:bolder;border:1px solid #cccccc"|&nbsp;
!width="5%" style="text-align:center;font-weight:bolder;border:1px solid #cccccc"|必选
!width="20%" style="text-align:center;font-weight:bolder;border:1px solid #cccccc"|类型及范围
!width="65%" style="text-align:center;font-weight:bolder;border:1px solid #cccccc"|说明
|-
|style="text-align:center;font-weight:bolder;border:1px solid #cccccc"|{{{1}}}
|style="text-align:center;text-transform:lowercase;border:1px solid #cccccc"|{{{2}}}
|style="text-align:left;padding-left:5px;border:1px solid #cccccc"|{{{3}}}
|style="text-align:left;padding-left:5px;border:1px solid #cccccc"|{{{4}}}
{{api_args|source|true|string|申请应用时分配的AppKey，调用接口时候代表应用的唯一身份。（采用OAuth授权方式不需要此参数）}}
{{{params}}}
|}
== 注意事项 ==
{{{useAge}}}
== 调用示例 ==
;XML:
:curl -u "username:password" {{{postParam}}} "http://api.t.sina.com.cn/{{{uri}}}.xml?source=appkey{{{getParam}}}"
;JSON:
:curl -u "username:password" {{{postParam}}} "http://api.t.sina.com.cn/{{{uri}}}.json?source=appkey{{{getParam}}}"
== 返回结果  ==
{{{result}}}
== 其他 ==
{{{otherInfo}}}
