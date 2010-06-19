<?xml version="1.0" encoding="gb2312"?>

<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output encoding="gb2312" indent="yes"/>

<xsl:template match="/">
<items>
  <xsl:for-each select="//tr[not(.//th) and not(.//span)]">
  <item>
    <author><xsl:value-of select="td[3]"/></author>
    <title><xsl:value-of select="td[6]"/></title>
    <url><xsl:value-of select="td[6]/a[2]/@href"/></url>
    <date><xsl:value-of select="td[4]"/></date>
  </item>
  </xsl:for-each>
</items>
</xsl:template>

</xsl:stylesheet>