package com.lurencun.http;

import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author : 桥下一粒砂
 * @email : chenyoca@gmail.com
 * @date : 2012-10-22
 * @desc : 基于Executor框架的线程池管理器
 */
public class ThreadPoolManager {

	public final static int INALID_REQUEST = -1;
	
	public final static int CORE_POOL_SIZE = 3;
	public final static int MAX_POOL_SIZE = CORE_POOL_SIZE * 3;
	public final static int KEEP_ALIVE_TIME_IN_S = 3 * 10;
	private final static int TASK_SCHEDULE_DELAY = 6 * 1000;
	
	private ConcurrentHashMap<Integer, Future<?>> mTaskHolder;
	private int mRequestTaskIdPool;
	private final ThreadPoolExecutor executor ;
	private final Timer cleanNullReferenceDaemon;
	
	{
		SynchronousQueue<Runnable> queue = new SynchronousQueue<Runnable>();
		executor  = new ThreadPoolExecutor(
				CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME_IN_S, TimeUnit.SECONDS,
				queue, new ThreadPoolExecutor.CallerRunsPolicy());
		cleanNullReferenceDaemon = new Timer(true);
		mTaskHolder = new ConcurrentHashMap<Integer, Future<?>>();
		// 开启空引用自检任务
		cleanNullReferenceDaemon.schedule(new TimerTask() {
			@Override
			public void run() {
				Iterator<Map.Entry<Integer, Future<?>>> iter = mTaskHolder.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry<Integer, Future<?>> entry = (Map.Entry<Integer, Future<?>>) iter.next();
					Future<?> task = entry.getValue();
					int key = entry.getKey();
					if(task.isDone() || task.isCancelled()){
						mTaskHolder.remove(key);
					}
				}
			}
		}, TASK_SCHEDULE_DELAY);
	}
	
	/**
	 * 提交一个任务
	 * @param r Runnable实现
	 * @return 一个任务包装对象
	 */
	public int submit(Runnable r){
		int taskId = mRequestTaskIdPool++;
		Future<?> task = executor.submit(r);
		mTaskHolder.put(taskId, task);
		return taskId;
	}
	
	/**
	 * 取消某个线程任务
	 * @param taskId 任务ID
	 * @param interruptRunning 是否中断任务
	 */
	public void cancel(int taskId,boolean interruptRunning){
		if(mTaskHolder.containsKey(taskId)){
			Future<?> task = mTaskHolder.get(taskId);
			if(!task.isDone() && !task.isCancelled()){
				task.cancel(interruptRunning);
			}
		}
	}
	
	/**
	 *  取消某个线程任务，如果任务正在执行，则中断运行。
	 * @param taskId
	 */
	public void cancel(int taskId){
		cancel(taskId,true);
	}
	
	/**
	 * 销毁线程池
	 */
	public void destory(){
		executor.shutdownNow();
	}
}
