USE agenthub;

---
-- ALTER TABLE purchaseinfo ADD FOREIGN KEY (cid) REFERENCES customers(cid);
-- ALTER TABLE purchaseinfo ADD FOREIGN KEY (pid) REFERENCES products(pid);
-- ALTER TABLE customers ADD FOREIGN KEY (sid) REFERENCES agents(sid);
-- ALTER TABLE customers DROP FOREIGN KEY customers_ibfk_1;
---

DROP TABLE IF EXISTS `purchaseinfo`;
DROP TABLE IF EXISTS `customers`;
DROP TABLE IF EXISTS `agents`;
DROP TABLE IF EXISTS `products`;
DROP TABLE IF EXISTS `users`;
--
-- Table structure for table `users`
--
CREATE TABLE `users` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `fullname` VARCHAR(50) NOT NULL,
  `email` VARCHAR(50) NOT NULL,
  `phone` VARCHAR(15) NOT NULL,
  `username` VARCHAR(20) NOT NULL,
  `password` VARCHAR(200) NOT NULL,
  `role` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`)
);

--
-- Dumping data for table `users`
--
INSERT INTO `users` (`id`, `fullname`, `email`, `phone`, `username`, `password`, `role`) VALUES
(12, 'Nur Inqsyira bt Zamri', 'cira@gmail.com', '0194732486', 'user1', 'cc03e747a6afbbcbf8be7668acfebee5', 'ADMINISTRATOR'),
(13, 'Daniel Suhaimi', 'dan@gmail.com', '0123456789', 'user2', 'cc03e747a6afbbcbf8be7668acfebee5', 'AGENT'),
(14, 'Mohamed Arique bin Mohd Aziyen', 'arique@gmail.com', '9876543210', 'user3', 'cc03e747a6afbbcbf8be7668acfebee5', 'AGENT');

--
-- Table structure for table `products`
--
CREATE TABLE `products` (
  `pid` INT(11) NOT NULL AUTO_INCREMENT,
  `productcode` VARCHAR(7) NOT NULL,
  `productname` VARCHAR(50) NOT NULL,
  `agentprice` DOUBLE NOT NULL,
  `sellingprice` DOUBLE NOT NULL,
  PRIMARY KEY (`pid`)
);

--
-- Dumping data for table `products`
--
INSERT INTO `products` (`pid`, `productcode`, `productname`, `agentprice`, `sellingprice`) VALUES
(01, 'prod1', 'The City Works Tokyo Notebook', 79, 95),
(02, 'prod2', 'The City Works Melbourne Notebook', 89, 109),
(03, 'prod3', 'The City Works Malaysia Notebook', 99, 119),
(04, 'prod4', 'Lico Notebook in Terracotta', 95, 115),
(05, 'prod5', 'Lico Notebook in Brown', 105, 125),
(06, 'prod6', 'Lico Notebook in Sand', 115, 135);

--
-- Table structure for table `agents`
--
CREATE TABLE `agents` (
  `sid` INT(11) NOT NULL AUTO_INCREMENT,
  `agentcode` VARCHAR(7) NOT NULL,
  `fullname` VARCHAR(50) NOT NULL,
  `email` VARCHAR(50) NOT NULL,
  `phone` VARCHAR(15) NOT NULL,
  PRIMARY KEY (`sid`)
);

--
-- Dumping data for table `agents`
--
INSERT INTO `agents` (`sid`, `agentcode`, `fullname`, `email`, `phone`) VALUES
(69, 'age5', 'Daniel Suhaimi', 'dan@gmail.com', '0123456789'),
(68, 'age4', 'Mohamed Arique bin Mohd Aziyen', 'arique@gmail.com', '9876543210');

--
-- Table structure for table `customers`
--
CREATE TABLE `customers` (
  `cid` INT(11) NOT NULL AUTO_INCREMENT,
  `customerCode` VARCHAR(7) NOT NULL,
  `customerName` VARCHAR(50) NOT NULL,
  `phone` VARCHAR(15) NOT NULL,
  `shippingAddress` VARCHAR(100) NOT NULL,
  `sid` INT(11) NOT NULL, 
  `customerNotes` VARCHAR(200) NOT NULL,
  PRIMARY KEY (`cid`),
  FOREIGN KEY (sid) REFERENCES agents(sid)
);

--
-- Dumping data for table `customers`
--
INSERT INTO `customers` (`cid`, `customerCode`, `customerName`, `phone`, `shippingAddress`, `sid`, `customerNotes`) VALUES
(1, 'C001', 'Mr. Nava', '011123456789', 'Cyberjaya, Selangor', 68, 'Followed up on the order of customized notebooks. Awaiting confirmation from production team. ' );

--
-- Table structure for table `purchaseinfo`
--
CREATE TABLE `purchaseinfo` (
  `purchaseid` INT(11) NOT NULL AUTO_INCREMENT,
  `cid` INT(11) NOT NULL,
  `pid` INT(11) NOT NULL,  -- Foreign key referencing products table
  `productname` VARCHAR(50) NOT NULL,
  `quantity` INT(100) NOT NULL,
  `purchaseDate` VARCHAR(200) NOT NULL,
  `totalcost` DOUBLE NOT NULL,
  PRIMARY KEY (`purchaseid`),
  FOREIGN KEY (cid) REFERENCES customers(cid),
  FOREIGN KEY (pid) REFERENCES products(pid)
);

--
-- Dumping data for table `purchaseinfo`
--
INSERT INTO `purchaseinfo` (`purchaseid`, `cid`, `pid`, `productname`, `quantity`, `purchaseDate`, `totalcost`) VALUES
(19, 1, 1, 'The City Works Tokyo Notebook', 2, '2024-05-20 12:24:34', '190.0'),
(20, 1, 2, 'The City Works Melbourne Notebook', 1, '2024-05-21 03:24:34', '109.0'),
(21, 1, 3, 'The City Works Malaysia Notebook', 3, '2024-05-22 02:24:34', '357.0');

-- Create Admin View
CREATE OR REPLACE VIEW admin_purchaseinfo AS
SELECT 
    p.purchaseid, p.cid, p.pid, p.quantity, p.purchaseDate, p.totalcost,
    c.customerName, c.phone AS customerPhone, c.shippingAddress, c.customerNotes,
    a.fullname AS agentName, a.email AS agentEmail, a.phone AS agentPhone
FROM 
    purchaseinfo p
JOIN 
    customers c ON p.cid = c.cid
JOIN 
    agents a ON c.sid = a.sid;

-- Create Agent View
CREATE OR REPLACE VIEW agent_purchaseinfo AS
SELECT 
    p.purchaseid, p.cid, p.pid, p.quantity, p.purchaseDate, p.totalcost,
    c.customerName, c.phone AS customerPhone, c.shippingAddress, c.customerNotes,
    a.fullname AS agentName, a.email AS agentEmail, a.phone AS agentPhone
FROM 
    purchaseinfo p
JOIN 
    customers c ON p.cid = c.cid
    

JOIN 
    agents a ON c.sid = a.sid
WHERE 
    a.email = CURRENT_USER();
