<?xml version="1.0" encoding="gb2312"?>

<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output encoding="gb2312" indent="yes"/>

<xsl:template match="/">
<items>
  <xsl:for-each select="/html[1]/body[1]//table[count(./tr)>15]/tr/td[1]">
  <item>
    <author><xsl:value-of select="substring-before(font[1], ' ')"/></author>
    
    <title><xsl:value-of select="a[1]"/></title>
    
    <url><xsl:value-of select="a[1]/@href"/></url>
    <date><xsl:value-of select="substring-after(font[1], ' ')"/></date>
  </item>
  </xsl:for-each>
</items>
</xsl:template>

</xsl:stylesheet>