<#list dataMap?keys as pcate>
==${pcate.vc2catename}==
<#list dataMap[pcate]?keys as subcate>
===${subcate.vc2catename}===
<#list dataMap[pcate][subcate] as spec>
[[${spec.getResourcePath()}]] ${spec.vc2shortdesc}
</#list>
</#list>
</#list>