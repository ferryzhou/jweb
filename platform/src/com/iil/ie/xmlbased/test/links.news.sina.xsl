<?xml version="1.0" encoding="gb2312"?>

<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output encoding="gb2312" indent="yes"/>

<xsl:template match="/">
<xsl:for-each select="//text()[normalize-space(.)='时尚类杂志封面秀']/following::*[count(.//a)&gt;2][1]//a">
<a>
  <xsl:attribute name="href"><xsl:value-of select="@href"/></xsl:attribute>
  <xsl:value-of select="."/>
</a>
</xsl:for-each>
</xsl:template>
<xsl:template match="text()"/>

</xsl:stylesheet>