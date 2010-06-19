<?xml version="1.0" encoding="gb2312"?>

<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output encoding="gb2312" indent="yes"/>

<xsl:template match="/html[1]/body[1]/center[1]/table[7]/tbody[1]/tr[18]">
<hefei>
  <tonight>
  <condition><xsl:value-of select="td[3]/text()"/></condition>
  <wind><xsl:value-of select="td[4]/text()"/></wind>
  <low><xsl:value-of select="td[5]/text()"/></low>
  </tonight>
  <tomorrow>
  <condition><xsl:value-of select="td[6]/text()"/></condition>
  <wind><xsl:value-of select="td[7]/text()"/></wind>
  <low><xsl:value-of select="td[8]/text()"/></low>
  </tomorrow>
</hefei>
</xsl:template>
<xsl:template match="text()"/>

</xsl:stylesheet>