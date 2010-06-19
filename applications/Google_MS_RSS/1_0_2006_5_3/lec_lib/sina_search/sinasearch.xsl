<?xml version="1.0" encoding="gb2312"?>

<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output encoding="gb2312" indent="yes"/>

<xsl:template match="/">
<items>
  <xsl:for-each select="/html[1]/body[1]/div[1]/table/tr[1]/td[2]">
  <item>
    <author><xsl:value-of select="span[2]/a[1]"/></author>
    <class2><xsl:value-of select="span[2]/a[2]"/></class2>
    <title><xsl:value-of select="font[1]/a[1]"/></title>
    <content><xsl:value-of select="span[1]"/></content>
    <url><xsl:value-of select="font[1]/a[1]/@href"/></url>
    <date><xsl:value-of select="font[1]/font[1]"/></date>
  </item>
  </xsl:for-each>
</items>
</xsl:template>

</xsl:stylesheet>