#install.packages('RMySQL')
#connect to sql
library('RMySQL')

db_address = "localhost"
db_user = "root"
db_pass = "wocao"
db_name = "pdata"

con = dbConnect(RMySQL::MySQL(), host = db_address, user = db_user, 
                password = db_pass, dbname = db_name)
dbListTables(con)
#read the data
a<-read.table("breast-cancer-wisconsin.data.txt", sep=",", 
              col.names=c("ID", "A2", "A3", "A4", "A5","A6","A7","A8","A9","A10","C"), 
              fill=FALSE, 
              strip.white=TRUE)

dbWriteTable(con, "datamining", a[,2:11],overwrite = TRUE)
dbSendQuery(con, "SET NAMES utf8")
query = "SELECT * FROM datamining;"
data = dbGetQuery(con, query)
data = data[,2:12]


#fix the data and save it to a new table name fixeddata
b = data[data$A7 == '?', 1:2]
data[data$A7 == '?', 7] = 4
data[, 7]=as.numeric(as.character(data[, 7]))
fixeddata = data
dbWriteTable(con, "fixeddata", fixeddata[,2:11],overwrite = TRUE)

#plot the fixed data
par(mfrow = c(2,5))
for (i in 2:11){
  hist(fixeddata[ , i],xlab = 'Range',main = paste("Attribute V",i), breaks = c(0,1,2,3,4,5,6,7,8,9,10))
}

#build the matrix for mean, median, mode, variance of each attribute
C = matrix(
  rep(0,36),
  nrow = 9,
  ncol = 4
)

for (i in 2:10) {
  a = mean(fixeddata[,i])
  b = median(fixeddata[,i])
  c = tail(names(sort(table(fixeddata[,i]))), 1)
  d = var(fixeddata[,i])
  C[i-1,1] = a
  C[i-1,2] = b
  C[i-1,3] = c
  C[i-1,4] = d
}

#Correlation Coefficient and clean the data and sava it to a 
#new table called clean table
cor(fixeddata[, 2:10], use="complete.obs", method="kendall") 

da = fixeddata
fixeddata$A3 <- NULL
cleandata = fixeddata

dbWriteTable(con, "cleandata", fixeddata[,2:10],overwrite = TRUE)

#this part gave me the mean of the nomissing value
#althought I dont use it in the final version of my code
#the mean of the nomissing value is 3.55
#make it a integer so it's 4
#missingline = a[ a$A7 == '?',]
#unmissningline = a[ a$A7 != '?', 7]
#asint = as.numeric(as.character(unmissningline))
#asintmi = as.numeric(as.character(4))
#for (i in 1:10) {
 # hist(a[,i])
#}
