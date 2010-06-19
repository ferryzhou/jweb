<?xml version="1.0" encoding="gb2312"?>

<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output encoding="gb2312" indent="yes"/>

<xsl:template match="/">
<items>
  <xsl:for-each select="/html[1]/body[1]/center[1]/table[4]/tr[1]/td[3]/table[2]/tr[2]/td[1]/ul[1]/li">
  <item>
   
    <title><xsl:value-of select="a[1]"/></title>
    
    <url><xsl:value-of select="a[1]/@href"/></url>
    <date><xsl:value-of select="a[1]/@href"/></date>
  </item>
  </xsl:for-each>
</items>
</xsl:template>

</xsl:stylesheet>