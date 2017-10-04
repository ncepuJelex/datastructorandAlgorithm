package com.jel.tech.learn.ch05;

import java.io.File;
import java.util.Scanner;

/**
 * 统计磁盘大小
 *
 * @author jelex.xu
 * @date 2017年10月4日
 */
public class DiskSpace {

	public static long diskUsage(File root) {
		// 该文件目录自身所占用的大小(bytes)
		long total = root.length();
		// 遍历该目录底下的entries
		if (root.isDirectory()) {
			for (String childName : root.list()) {
				File child = new File(root, childName);
				total += diskUsage(child);
			}
		}
		System.out.println(total + "\t" + root);
		return total;
	}

	/**
	 * Computes disk usage of the path given as a command line argument. Sample
	 * usage: java DiskSpace /Users/zhenhua/Downloads/JavaConcurrrencyPractice
	 */
	public static void main(String[] args) {
		String start;
		if (args.length > 0) {
			start = args[0];
		} else {
			System.out.print("Enter the starting location: ");
			start = new Scanner(System.in).next();
		}
		diskUsage(new File(start));
	}
	/*
	 * running result:
	 * Enter the starting location: /Users/zhenhua/Downloads/JavaConcurrrencyPractice
		6148	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/.DS_Store
		4998	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-annotations-src.jar
		2250	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-annotations.jar
		6148	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/.DS_Store
		102	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/META-INF/MANIFEST.MF
		204	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/META-INF
		6148	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/.DS_Store
		6148	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/.DS_Store
		2192	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/Animals.java
		733	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/AtomicPseudoRandom.java
		743	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/AttributeStore.java
		2666	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/BackgroundTask.java
		595	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/BarrierTimer.java
		1045	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/BaseBoundedBuffer.java
		738	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/BetterAttributeStore.java
		575	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/BetterVector.java
		1202	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/BoundedBuffer.java
		981	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/BoundedExecutor.java
		899	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/BoundedHashSet.java
		784	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/BrokenPrimeProducer.java
		1600	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/CachedFactorizer.java
		470	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/CasCounter.java
		1539	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/CasNumberRange.java
		2141	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/CellularAutomata.java
		1073	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/CheckForMail.java
		2111	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/ConcurrentPuzzleSolver.java
		1072	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/ConcurrentStack.java
		1605	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/ConditionBoundedBuffer.java
		871	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/ConnectionDispenser.java
		1715	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/CooperatingDeadlock.java
		1987	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/CooperatingNoDeadlock.java
		527	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/Counter.java
		915	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/CountingFactorizer.java
		339	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/CountingSheep.java
		2650	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/DeadlockAvoidance.java
		1249	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/DelegatingVehicleTracker.java
		1452	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/DemonstrateDeadlock.java
		579	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/DoubleCheckedLocking.java
		1925	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/DynamicOrderDeadlock.java
		387	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/EagerInitialization.java
		1397	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/Factorizer.java
		1779	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/FutureRenderer.java
		1277	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/GrumpyBoundedBuffer.java
		1149	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/GuiExecutor.java
		676	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/HiddenIterator.java
		373	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/Holder.java
		2821	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/ImprovedList.java
		2630	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/IndexingService.java
		1870	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/InduceLockOrder.java
		638	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/InterruptibleLocking.java
		651	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/LaunderThrowable.java
		440	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/LazyInitRace.java
		649	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/LeftRightDeadlock.java
		1567	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/LifecycleWebServer.java
		1750	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/LinkedQueue.java
		5030	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/ListenerExamples.java
		881	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/ListHelpers.java
		1918	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/LogService.java
		1192	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/LogWriter.java
		1319	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/Memoizer.java
		1012	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/Memoizer1.java
		668	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/Memoizer2.java
		1110	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/Memoizer3.java
		1321	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/MonitorVehicleTracker.java
		377	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/MutableInteger.java
		407	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/MutablePoint.java
		1811	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/MyAppThread.java
		450	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/MyThreadFactory.java
		747	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/NoncancelableTask.java
		431	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/NonreentrantDeadlock.java
		573	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/NoVisibility.java
		948	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/NumberRange.java
		902	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/OneShotLatch.java
		770	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/OneValueCache.java
		625	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/OutOfTime.java
		527	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/PersonSet.java
		326	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/Point.java
		802	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/PossibleReordering.java
		1152	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/Preloader.java
		1311	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/PrimeGenerator.java
		744	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/PrimeProducer.java
		413	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/PrivateLock.java
		2742	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/ProducerConsumer.java
		269	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/PseudoRandom.java
		1048	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/PublishingVehicleTracker.java
		2690	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/PutTakeTest.java
		339	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/Puzzle.java
		670	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/PuzzleNode.java
		952	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/PuzzleSolver.java
		1179	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/ReaderThread.java
		1982	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/ReadWriteMap.java
		752	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/ReentrantLockPseudoRandom.java
		1652	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/Renderer.java
		1417	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/RenderWithTimeBudget.java
		471	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/ResourceFactory.java
		461	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/SafeLazyInitialization.java
		851	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/SafeListener.java
		576	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/SafePoint.java
		576	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/SafeStates.java
		575	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/SafeVectorHelpers.java
		302	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/Secrets.java
		1580	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/SemaphoreBoundedBuffer.java
		1212	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/SemaphoreOnLock.java
		282	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/Sequence.java
		1100	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/SequentialPuzzleSolver.java
		964	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/ServerStatusAfterSplit.java
		819	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/ServerStatusBeforeSplit.java
		788	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/SimulatedCAS.java
		800	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/SingleThreadRenderer.java
		634	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/SingleThreadWebServer.java
		983	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/SleepyBoundedBuffer.java
		2537	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/SocketUsingTask.java
		812	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/StatelessFactorizer.java
		1281	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/StripedMap.java
		257	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/StuffIntoPublic.java
		1149	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/SwingUtilities.java
		1214	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/SynchronizedFactorizer.java
		416	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/SynchronizedInteger.java
		947	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/TaskExecutionWebServer.java
		609	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/TaskRunnable.java
		2185	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/TestBoundedBuffer.java
		1160	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/TestHarness.java
		1482	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/TestThreadPool.java
		606	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/ThisEscape.java
		1229	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/ThreadDeadlock.java
		813	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/ThreadGate.java
		329	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/ThreadPerTaskExecutor.java
		805	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/ThreadPerTaskWebServer.java
		805	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/ThreeStooges.java
		2116	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/TimeBudget.java
		999	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/TimedLocking.java
		1719	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/TimedPutTakeTest.java
		950	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/TimedRun.java
		667	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/TimedRun1.java
		1369	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/TimedRun2.java
		1538	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/TimingThreadPool.java
		1649	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/TrackingExecutor.java
		1980	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/TransformingSequential.java
		457	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/UEHLogger.java
		1329	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/UnsafeCachingFactorizer.java
		973	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/UnsafeCountingFactorizer.java
		472	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/UnsafeLazyInitialization.java
		304	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/UnsafeSequence.java
		312	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/UnsafeStates.java
		494	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/UnsafeVectorHelpers.java
		750	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/ValueLatch.java
		959	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/VisualComponent.java
		1094	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/VolatileCachedFactorizer.java
		2307	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/WebCrawler.java
		330	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/WithinThreadExecutor.java
		625	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/WorkerThread.java
		509	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples/XorShift.java
		162962	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip/examples
		169246	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net/jcip
		175530	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src/net
		182052	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src
		84699	/Users/zhenhua/Downloads/JavaConcurrrencyPractice/jcip-examples-src.jar
		280385	/Users/zhenhua/Downloads/JavaConcurrrencyPractice

		目录情况如下：
		Jelex:JavaConcurrrencyPractice zhenhua$ ls -la
			total 208
			drwxr-xr-x@  7 zhenhua  staff    238  8 18 23:07 .
			drwx------+ 57 zhenhua  staff   1938 10  3 20:58 ..
			-rw-r--r--@  1 zhenhua  staff   6148  8 18 23:07 .DS_Store
			-rw-r--r--@  1 zhenhua  staff   4998  6  7 22:06 jcip-annotations-src.jar
			-rw-r--r--@  1 zhenhua  staff   2250  6  7 22:06 jcip-annotations.jar
			drwxr-xr-x   5 zhenhua  staff    170  8 18 23:07 jcip-examples-src
			-rw-r--r--@  1 zhenhua  staff  84699  6  7 22:06 jcip-examples-src.jar
			Jelex:JavaConcurrrencyPractice zhenhua$ ls -lai
	 */

}
