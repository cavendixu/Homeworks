#include"stdafx.h"
#include<iostream>
#include<queue>
#include<string>

using namespace std;
const int inf = 2147483645;

struct node{
	int days, remain, c;
	bool operator <(const node&cmp)const {
		return c > cmp.c;
	}
};
struct node2 {//cur是当前天数，end指已经买完了前几天的雪糕，c是cost
	int cur, end, c;
	bool operator <(const node&cmp)const {
		return c > cmp.c;
	}
};
int day, demands, cap, ship, p, night_cost;
int demand[1000];
int dp[1000][1000];

int dij() {//实际优化过的BFS
	priority_queue<node> q;
	q.push({ 0,0,0 });
	while (!q.empty()) {

		node tem = q.top();
		q.pop();
		int days, remain, cost;
		days = tem.days;
		remain = tem.remain;
		cost = tem.c;

		int min_buy = demand[days + 1] - remain;
		int max_buy = cap - remain;
		if (min_buy <= 0)
			min_buy = 0;
		if (days == day) {
			
			return cost;
		}
		for (int i = min_buy; i <= max_buy; i++) {
			int num_store = remain - demand[days + 1] + i;
			int newcost = cost + i*p + num_store*night_cost;
			if (i > 0)
				newcost += ship;
			if (newcost < dp[days + 1][num_store]) {
				dp[days + 1][num_store] = newcost;
				q.push({ days + 1,num_store,newcost });
			}

		}
	}
}

int DFS(node2 tem) {//每次DFS相当于一个分叉， 用min记录各个分叉的最小值

	int min = inf;
	int cur, end, cost;
	cur = tem.cur;
	end = tem.end;
	cost = tem.c;
	if (cur == day) {//如果已经是最后一天，就直接返回
		return cost;
	}
	cur++;//进入下一天

	//暴力搜索所有情况的话，就是每天买demand[i]到capacity雪糕数都试一遍，速度比较慢，数据量比较大的话
	//肯定过不了。 Dij()就是用这种办法暴力搜索，只不过用heap优化了一下，优先选择花费较小的情况。可能可
	//以过
	//而我们要知道，一次买多天的意义在于减少配送次数，而只有库存可以完全满足某一天的demand，当天才可以
	//不用买。 所以我们大胆地猜测 =。= 【我也不会证】 第i天购买数量的最优解，只可能在0，demand[i],
	//demand[i]+demand[i+1],demand[i]+demand[i+1]...之间


	//从不买，到多买i天的库存，退出条件为，已经买完最后一天的，或者capacity不够
	for (int i = 0; end + i <= day; i++) {
		//end>=cur,说明前一天已经提前买完了现在这天需要的量，所以不用新买，只计算overnight_cost就好
		if (i == 0 && end >= cur) {
			int unit = 0;
			if (cur< end) {
				for (int j = cur + 1; j <= end; j++) {
					unit += demand[j];
				}
			}
			int value=DFS({ cur,end,cost+unit*night_cost });
			if (min > value)
				min = value;
		}
		else if(i!=0){
			int unit = 0;
			for (int j = cur; j <= end + i; j++) //计算如果多买i天库存，会不会爆capacity
				unit += demand[j];
			if (unit > cap)//会的话就退出for循环
				break;
			cost += demand[end + i] * p;//不会就增加cost
			unit -= demand[cur];//当前这天的被消耗，不计入库存
			int value=DFS({ cur,end + i,cost + ship + unit * night_cost });
			if (min > value)
				min = value;
		}
	}
	return min;
}
int main() {
	cin >> day;
	for (int i = 1; i <= day; i++)
		cin >> demand[i];
	cin >> cap >> ship >> p >> night_cost;//输入

	//for (int i = 0; i <= day; i++)//记录第i天，存了j个的最小cost
	//	for (int j = 0; j <= cap; j++)
	//		dp[i][j] = inf;

	cout << DFS({ 0,0,0 }) << endl;//初始状态，第0天，只买了0天的雪糕，花费是0

}