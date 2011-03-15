==${dstruct.vc2version}.${dstruct.vc2structname}==
${dstruct.vc2desc}
==字段列表==
{| border="1" cellspacing="0" cellpadding="0" width="100%" class="parameters" style="border-color: #CCCCCC;"
|-
!width="10%" style="text-align:center;font-weight:bolder;border:1px solid #cccccc"|名称
!width="5%" style="text-align:center;font-weight:bolder;border:1px solid #cccccc"|私有
!width="20%" style="text-align:center;font-weight:bolder;border:1px solid #cccccc"|类型
!width="15%" style="text-align:center;font-weight:bolder;border:1px solid #cccccc"|示例值
!width="50%" style="text-align:center;font-weight:bolder;border:1px solid #cccccc"|说明
<#if (dstruct.tstructfieldSet?exists)&&(dstruct.tstructfieldSet?size > 0)>
<#list dstruct.tstructfieldSet as field>
|-
|style="text-align:center;font-weight:bolder;border:1px solid #cccccc"|${field.vc2fieldname}
|style="text-align:center;text-transform:lowercase;border:1px solid #cccccc"|${field.getIsPrivate()?string}
|style="text-align:left;padding-left:5px;border:1px solid #cccccc"|<#if field.enumDataTypes.isStruct()>[[DataStruct:${field.numdatastructid.vc2version}.${field.numdatastructid.vc2structname?cap_first}|${field.numdatastructid.vc2structname}]]<#if field.enumDataTypes.name()=="ARRAY">[]</#if><#else>${field.enumDataTypes.getName()}</#if>
|style="text-align:left;padding-left:5px;border:1px solid #cccccc"|<#if field.vc2demovalue?exists>${field.vc2demovalue?default("")}</#if>
|style="text-align:left;padding-left:5px;border:1px solid #cccccc"|${field.vc2desc?default("")}
</#list>
</#if>
|}