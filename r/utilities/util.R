# -------------------------------
# Plot a dataset as raw file size
# -------------------------------
plot.as.raw <- function(df, encodings, fnames=NULL) {
  if(length(fnames) != 0){
    df.temp <- df[df$variable %in% encodings & df$file %in% fnames,]
  } else {
    df.temp <- df[df$variable %in% encodings,]
  }
  p <- ggplot(df.temp, aes(x=reorder(file, value), y=value, group=variable, colour=variable)) +
    geom_point(aes(shape=variable)) +
    geom_line() +
    scale_y_continuous("File Size in Bytes", labels = comma) +
    scale_x_discrete("File Sample") +
    guides(colour = guide_legend("Encoding"), shape = guide_legend("Encoding")) +
    scale_color_hue(l=50)
  
  if(length(unique(df.temp$file)) <= 25){
    p <- p+ theme(axis.text.x = element_text(angle=90, size=9))
  } else {
    p <- p + theme(axis.text.x = element_blank(), axis.ticks = element_blank())
  }
  print(p)
}


# ------------------------------
# Plot a dataset as a percentage
# ------------------------------
# first element in columns will becomes x-axis variable
# the remaining ones become the series of plots on y-axis
plot.as.percentage <- function(columns, set_ylim=FALSE, range=NULL){
  
  # Melt the data frame back to 'long' form so ggplot2
  # understands it better.
  df.temp <- melt(dfcp[columns], id=columns[1])
  
  
  p <- ggplot(data = df.temp, aes(x=base.size, y=value, color=variable)) +
    geom_point(aes(shape=variable)) +
    geom_line() +
    scale_y_continuous("% XML size", labels=percent_format()) + 
    guides(colour = guide_legend("Encoding"), shape = guide_legend("Encoding")) +
    scale_color_hue(l=50)
  
  # To show a thin black line at 100% (i.e. full XML size),
  # set_ylim should equal true. Otherwise, ggplot2 will scale the 
  # y-axis to fit the contents.
  if(set_ylim){
    p <- p + coord_cartesian(ylim = c(0,1.1)) +
      geom_hline(yintercept=1, aes(size=0.8, colour="#555555"))
  } else {
    p <- p + coord_cartesian(ylim = c(0, max(df.temp$value))*1.1)
  }
  
  # Manually set x-axis domain. Useful for 
  if(length(range) == 2){
    p <- p + scale_x_continuous("Original XML size in bytes", labels = comma, limits=range)
  } else {
    p <- p + scale_x_continuous("Original XML size in bytes", labels = comma)
  }

  # Global font change - a bit more compact
  p <- p + theme(text=element_text(family="Arial Narrow"))
  return(p)
}


# ------------------------------------------------------
# Plot a dataset as a percentage of a specified encoding
# ------------------------------------------------------
# Parameters:

#   1) df:  is a melted dataframe of results
#   2) baseline:  value will become x-axis variable e.g. to plot plaintext
#      XML size on the x-axis, pass "xml". In real world terms, the baseline
#      should reflect the encoding that's already passing over the network.
#   3) series:  which encodings to display as series on the plot 
#   4) x.range:  The maximum and minimum values for the x-axis. Useful for
#      narrowing down to interesting parts of plots, like the small end of
#      the scale. Options are:
#        - [min, max]: A number array of length 2, indicating the minimum and
#                      maximum values for the x-axis
#        - "log":      Show full range of dataset on x-axis, using a base-10
#                      logarithmic scale
#        - "auto":     The default. Shows full range of dataset on x-axis,
#                      using a continuous scale.
filesize.vs.compaction <- function(df, baseline, series,  x.range="auto"){
  
  # Cast data and evaluate sizes as percentage of specified encoding
  dfc <- dcast(df, file ~ variable)
  dfcp <- data.frame(c(dfc[1], dfc[-1] / dfc[,baseline]))
  dfcp$base.size <- dfc[,baseline]
  
  # Summary data not related to plotting
  print(paste("Series:  ", paste(series, collapse=", ")))
  print(paste("Baseline: ", baseline))
  print(summary(dfcp[c(series)]))
  
  # Melt the data frame back to 'long' form so ggplot2 likes it better.
  dfcp <- melt(dfcp[c(series,"base.size")], id.vars="base.size")
    
  # Generate labels for the x and y axes using baseline value
  ylabel <- paste("Compaction (% ", toupper(baseline), " size)")
  xlabel <- paste("Original ", toupper(baseline), " size (bytes, log scale)")
  
  # Plot the data and core aesthetics
  p <- ggplot(data = dfcp, aes(x=base.size, y=value, color=variable)) +
    geom_point(size=0.7, shape=3)  +
    geom_line(size=0.25) + # , aes(linetype=variable)
    guides(colour = guide_legend("Encoding"),
           shape = guide_legend("Encoding"),
           linetype= guide_legend("Encoding"))
  
  # Adjust the y-scale
  p <- p + scale_y_continuous(ylabel, breaks=seq(0,10,0.1), labels=percent_format())
  p <- p + coord_cartesian(ylim=c(-0.05, max(dfcp$value)*1.05))
  if(max(dfcp$value) > 1){ p <- p + geom_hline(yintercept=1) }
  
  # Adjust the x-scale
  if(length(x.range) == 2){
    p <- p + scale_x_continuous(xlabel, labels = comma, limits=range)
  } else if(x.range == "log") {
    
    # A less-readable format showing breaks as 10^1, 10^2, ... 
    # p <- p + scale_x_log10(xlabel, breaks=trans_breaks("log10", function(x) 10^x), labels = trans_format("log10", math_format(10^.x)))
    p <- p + scale_x_log10(xlabel, 
                           breaks=c(1,5,10,50,100,500,1000,5000,10000,50000,100000,500000,1000000,5000000,10000000,50000000,100000000,500000000,1000000000,5000000000,10000000000),
                           labels=c("1B","5B","10B","50B","100B","500B","1K","5K","10K","50K","100K","500K","1M","5M","10M","50M","100M","500M","1G","5G","10GB"))
  } else {
    p <- p + scale_x_continuous(xlabel, labels = comma)
  }
  
  return(p)
}


# ------------------------------------------------------------------------------------------
# Plot a dataset as a percentage of a specified encoding, and facet across multiple datasets
# ------------------------------------------------------------------------------------------
faceted.filesize.vs.compaction <- function(df, baseline, series, facet, x.range="auto"){

# Cast data and evaluate sizes as percentage of specified encoding
  dfc <- dcast(df, facet + file ~ variable)
  dfcp <- data.frame(c(dfc[1:2],  dfc[-(1:2)] / dfc[,baseline] ))
  dfcp$base.size <- dfc[,baseline]
  
  # Summary data not related to plotting
  print(paste("Series:  ", paste(series, collapse=", ")))
  print(paste("Baseline: ", baseline))
  print(summary(dfcp[c(series)]))
  
# Melt the data frame back to 'long' form so ggplot2 likes it better.
  dfcp <- melt(dfcp[c(series,"base.size", facet)], id.vars=c("base.size",facet))
  
  # Generate labels for the x and y axes using baseline value
  ylabel <- paste("Compaction (% ", toupper(baseline), " size)")
  xlabel <- paste("Original ", toupper(baseline), " size (bytes, log scale)")
  
  # Plot the data and core aesthetics
  p <- ggplot(data = dfcp, aes(x=base.size, y=value, color=variable)) +
    geom_point(size=0.7, shape=3)  +
    geom_line(size=0.25) + # , aes(linetype=variable)
    facet_grid(. ~ facet) +
    guides(colour = guide_legend("Encoding"),
           shape = guide_legend("Encoding"),
           linetype= guide_legend("Encoding"))
  
  # Adjust the y-scale
  p <- p + scale_y_continuous(ylabel, labels=percent_format(), breaks=seq(0,10,.1))
  p <- p + coord_cartesian(ylim=c(-0.05, max(dfcp$value)*1.05))
  if(max(dfcp$value) > 1){ p <- p + geom_hline(yintercept=1) }
  
  # Adjust the x-scale
  if(length(x.range) == 2){
    p <- p + scale_x_continuous(xlabel, labels = comma, limits=range)
  } else if(x.range == "log") {
    # 
    p <- p + scale_x_log10(xlabel, 
                           breaks=c(1,10,100,1000,10000,100000,1000000,10000000,100000000,1000000000,10000000000),
                           labels=c("1B","10B","100B","1K","10K","100K","1M","10M","100M","1G","10G"))
  } else {
    p <- p + scale_x_continuous(xlabel, labels = comma)
  }
  
  return(p)
}


# ----------------------------------------------------------------------
# Make some changes to the default ggplot2 output to make results better
# suited for print format.
# ----------------------------------------------------------------------
prettify.plot <- function(p, legend.placement="top.right"){
  
  font.size <- 8
  p <- p + scale_color_hue(l=50) +
    theme_bw(base_size = font.size) + 
    theme(line=element_line(size = 0.25),
          panel.grid.major = element_line(color = "grey"),
          panel.grid.minor = element_line(size=0.25),
          legend.key = element_rect(colour = "#ffffff"),# fill = "#ffffff"),
          legend.background = element_rect(color="grey50", size=0.25),
          legend.key.size = unit(font.size, "points"),
          legend.margin = unit(0.5, "points"),
          legend.title = element_text(lineheight=10))
  
  # Move the legend to a convenient corner
  if(legend.placement == "bottom.right"){ p <- p + theme(legend.justification=c(1,0), legend.position=c(1,0)) }
  if(legend.placement == "bottom.left"){ p <- p + theme(legend.justification=c(0,0), legend.position=c(0,0))  }
  if(legend.placement == "top.right"){ p <- p + theme(legend.justification=c(1,1), legend.position=c(1,1)) }
  if(legend.placement == "top.left"){ p <- p + theme(legend.justification=c(0,1), legend.position=c(0,1)) }
  if(legend.placement == "mid.right"){ p <- p + theme(legend.justification=c(1,0), legend.position=c(1,0.3)) }
  if(legend.placement == "mid.left"){ p <- p + theme(legend.justification=c(0,0), legend.position=c(0,0.4)) }
  return(p)
}




# ----------------------------------------------------------------------
# Get filename list for the N-M smallest elements of a specific encoding
# ----------------------------------------------------------------------
get.range.of.fnames <- function(lower, upper, encoding, df){
  df.temp <- df[df$variable == encoding,]   # Select all rows pertaining to the encoding
  df.temp <- arrange(df.temp, value)        # Sort the rows by encoding
  fnames <- df.temp[lower:upper, "file"]
  rm(df.temp)
  return(fnames)
}

# ------------------------------------------------------------------------------------
# Export a ggplot in PDF format that fits nicely on US Letter paper with 1.25" margins
# ------------------------------------------------------------------------------------
save.for.print <- function(image.folder, use.case, file.name){
  ggsave(file=file.path(image.folder, paste0(file.name, "-", use.case, ".pdf")), width=6, height=3.25, units="in")  
}
