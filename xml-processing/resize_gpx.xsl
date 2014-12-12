<?xml version="1.0" ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:gpx="http://www.topografix.com/GPX/1/1">
    <xsl:output method="xml" indent="yes"/>
    <xsl:strip-space elements="trkseg"/>
    
    <xsl:param name="i"/>
    
    <xsl:template match="/">
        <xsl:copy>
            <xsl:copy-of select="@*" />
            <xsl:apply-templates/>
        </xsl:copy>
    </xsl:template>
    
    <xsl:template match="gpx:*">
        <xsl:copy>
            <xsl:copy-of select="@*" />
            <xsl:apply-templates />
        </xsl:copy>
    </xsl:template>

    <xsl:template match="gpx:trkseg">
        <xsl:copy>
            <xsl:copy-of select="@*" />
            <xsl:apply-templates select="gpx:trkpt[position() &lt;= $i]"/>
        </xsl:copy>
    </xsl:template>

    <xsl:template match="gpx:trkpt">
        <xsl:copy>
            <xsl:copy-of select="@*" />
            <xsl:apply-templates/>
        </xsl:copy>
    </xsl:template>
    
</xsl:stylesheet>  

    