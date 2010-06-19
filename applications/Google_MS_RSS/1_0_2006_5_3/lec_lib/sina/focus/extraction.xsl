<?xml version="1.0" encoding="gb2312"?>

<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output encoding="gb2312" indent="yes"/>

<xsl:template match="/">
<items>
  <xsl:for-each select="//table[contains(tr[3]/td[2], '全文')]">
  <item>
   
    <title><xsl:value-of select="tr[1]/td[2]/a[1]"/></title>
    <author><xsl:value-of select="preceding-sibling::table[last()]/tr[1]/td[2]"/></author>
    <url><xsl:value-of select="tr[1]/td[2]/a[1]/@href"/></url>
    <content><xsl:value-of select="tr[2]/td[2]"/></content>
    <date><xsl:value-of select="tr[1]/td[2]/a[1]/@href"/></date>
  </item>
  </xsl:for-each>
</items>
</xsl:template>

</xsl:stylesheet>