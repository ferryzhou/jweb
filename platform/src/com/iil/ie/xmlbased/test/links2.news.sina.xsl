<?xml version="1.0" encoding="gb2312"?>

<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output encoding="gb2312" indent="yes"/>

<xsl:template match="/">
<xsl:variable name="title">今 日 十 大 热 门 话 题</xsl:variable>
<xsl:value-of select="$title"/>
<xsl:value-of select="//text()[contains(., $title)]"/>
<xsl:for-each select="//text()[string(.) = $title]/ancestor::*[starts-with(., $title)][count(.//a)&gt;2][1]//a">
<a>
  <xsl:attribute name="href"><xsl:value-of select="@href"/></xsl:attribute>
  <xsl:value-of select="."/>
</a>
</xsl:for-each>
</xsl:template>
<xsl:template match="text()"/>

</xsl:stylesheet>