On the bitcoin network nodes share their blockchains and the longest valid chain is accepted by the network. What’s to stop someone tampering with data in an old block then creating a whole new longer blockchain and presenting that to the network ?** Proof of work**. 

The hashcash proof of work system means it takes considerable time and computational power to create new blocks. Hence the attacker would need more computational power than the rest of the peers combined.


** 项目结构：**<br>
1.使用maven工程创建，Eclipse环境，Java语言编写

2.工程可能需要用到一个密码术包：Bouncy Castle，参考一下迷你教程将其导入Eclipse：
https://medium.com/@cryptokass/importing-bouncy-castle-into-eclipse-24e0dda55f21

** 功能： **<br>
入门级别的区块链示例，包括了
** 区块的生成和校验，事务的生成与校验，钱包的生成和计算，加密等 **，
功能逐步增加中，在main主方法中测试

** 参考博客： **<br>
https://medium.com/programmers-blockchain/creating-your-first-blockchain-with-java-part-2-transactions-2cdac335e0ce
