<#list dataMap?keys?sort as pcate>
==${pcate?split("||")?last}==
<#list dataMap[pcate]?keys?sort as subcate>
===${subcate?split("||")?last}===
<#list dataMap[pcate][subcate]?sort_by("numcateindex") as spec>
[[${spec.getResourcePath()}]] ${spec.vc2shortdesc}
</#list>
</#list>
</#list>