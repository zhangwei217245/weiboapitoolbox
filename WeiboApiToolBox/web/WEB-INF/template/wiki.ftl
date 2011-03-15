
== ${spec.resourcePath} ==
${spec.vc2shortdesc}
${spec.vc2maindesc}
== URL ==
<span style="text-transform:lowercase;font-weight:600">${spec.numbaseurlid.vc2baseurl}/${spec.resourcePath}</span>
== 支持格式 ==
<span style="text-transform:uppercase;font-weight:600">${format}</span>
== HTTP请求方式 ==
<span style="text-transform:uppercase;font-weight:600">${httpMethod}</span>
== 是否需要登录 ==
<span style="text-transform:lowercase;font-weight:600">${(spec.enumAuthType.name()!="NONE")?string}</span><br/>
关于授权机制，参见[[授权机制说明|授权机制声明]]
== 请求数限制 ==
<span style="text-transform:lowercase;font-weight:600">${(spec.enumRateLimit.name()!="NONE")?string}</span><br/>
关于请求数限制，参见[[Rate-limiting|接口访问权限说明]]
== 请求参数 ==
{| border="1" cellspacing="0" cellpadding="0" width="100%" class="parameters" style="border-color: #CCCCCC;"
|-
!width="10%" style="text-align:center;font-weight:bolder;border:1px solid #cccccc"|&nbsp;
!width="5%" style="text-align:center;font-weight:bolder;border:1px solid #cccccc"|必选
!width="20%" style="text-align:center;font-weight:bolder;border:1px solid #cccccc"|类型及范围
!width="65%" style="text-align:center;font-weight:bolder;border:1px solid #cccccc"|说明
<#if (sysparam?exists)&&(sysparam?size > 0)>
|-
|colspan="4" style="text-align:center;background-color:#eeeeee;font-weight:bold;border:1px solid #cccccc"|系统级参数
<#list sysparam as spar>
|-
|style="text-align:center;font-weight:bolder;border:1px solid #cccccc"|${spar.vc2paramname}
|style="text-align:center;text-transform:lowercase;border:1px solid #cccccc"|${spar.getIsRequired()?string}
|style="text-align:left;padding-left:5px;border:1px solid #cccccc"|${spar.enumDataTypes.getName()}<#if (spar.vc2range?exists)&&(spar.vc2range?length > 0)>:${spar.vc2range}</#if>
|style="text-align:left;padding-left:5px;border:1px solid #cccccc"|${spar.vc2desc}
</#list>
</#if>
<#if (spec.trequestparamSet?exists)&&(spec.trequestparamSet?size > 0)>
|-
|colspan="4" style="text-align:center;background-color:#eeeeee;font-weight:bold;border:1px solid #cccccc"|应用级参数
<#list spec.trequestparamSet as specparam>
|-
|style="text-align:center;font-weight:bolder;border:1px solid #cccccc"|${specparam.vc2paramname}
|style="text-align:center;text-transform:lowercase;border:1px solid #cccccc"|${specparam.getIsRequired()?string}
|style="text-align:left;padding-left:5px;border:1px solid #cccccc"|${specparam.enumDataTypes.getName()}<#if (specparam.vc2range?exists)&&(specparam.vc2range?length > 0)>:${specparam.vc2range}</#if>
|style="text-align:left;padding-left:5px;border:1px solid #cccccc"|${specparam.vc2desc}
</#list>
</#if>
|}
== 注意事项 ==
<#list spec.vc2cautions?split("|") as ct>
* ${ct?trim}
</#list>
== 调用示例 ==
<#if CURL_GET?exists>
;GET:
:curl<#if (spec.enumAuthType.name()!="NONE")> -u "username:password"</#if> ${CURL_GET}
</#if>
<#if CURL_POST?exists>
;POST:
:curl<#if (spec.enumAuthType.name()!="NONE")> -u "username:password"</#if> ${CURL_POST}
</#if>
<#if CURL_PUT?exists>
;PUT:
:curl<#if (spec.enumAuthType.name()!="NONE")> -u "username:password"</#if> ${CURL_PUT}
</#if>
<#if CURL_DELETE?exists>
;DELETE:
:curl<#if (spec.enumAuthType.name()!="NONE")> -u "username:password"</#if> ${CURL_DELETE}
</#if>
== 返回结果  ==
<#if (spec.tresponseSet?exists)&&(spec.tresponseSet?size > 0)>
{| border="1" cellspacing="0" cellpadding="0" width="100%" class="parameters" style="border-color: #CCCCCC;"
|-
!width="10%" style="text-align:center;font-weight:bolder;border:1px solid #cccccc"|名称
!width="5%" style="text-align:center;font-weight:bolder;border:1px solid #cccccc"|必选
!width="20%" style="text-align:center;font-weight:bolder;border:1px solid #cccccc"|类型
!width="65%" style="text-align:center;font-weight:bolder;border:1px solid #cccccc"|说明
<#list spec.tresponseSet as resp>
|-
|style="text-align:center;font-weight:bolder;border:1px solid #cccccc"|${resp.vc2responsename}
|style="text-align:center;text-transform:lowercase;border:1px solid #cccccc"|${resp.getIsRequired()?string}
|style="text-align:left;padding-left:5px;border:1px solid #cccccc"|<#if resp.enumDataTypes.isStruct()>[[DataStruct:${resp.numdatastructid.vc2version}.${resp.numdatastructid.vc2structname?cap_first}|${resp.numdatastructid.vc2structname}]]<#if resp.enumDataTypes.name()=="ARRAY">[]</#if><#else>${resp.enumDataTypes.getName()}</#if>
|style="text-align:left;padding-left:5px;border:1px solid #cccccc"|${resp.vc2desc}
</#list>
</#if>
