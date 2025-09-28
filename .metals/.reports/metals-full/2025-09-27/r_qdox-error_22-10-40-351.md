error id: file:///C:/Users/panlu/Documents/Cours/M1_Program_Fonction/java/app/bin/main/org/example/cours2/Cours2.java
file:///C:/Users/panlu/Documents/Cours/M1_Program_Fonction/java/app/bin/main/org/example/cours2/Cours2.java
### com.thoughtworks.qdox.parser.ParseException: syntax error @[14,5]

error in qdox parser
file content:
```java
offset: 308
uri: file:///C:/Users/panlu/Documents/Cours/M1_Program_Fonction/java/app/bin/main/org/example/cours2/Cours2.java
text:
```scala
package org.example.cours2;

public class Cours2 {
    static Comparator<String> scoreComparator = new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {
            return Integer.compare(getScore(o2), getScore(o1));
        }
    };

    st
    

    p@@ublic static void main(String[] args) {
        String[] players = {"Alice", "Bob", "Charlie", "Diana"};
        Arrays.sort(players, scoreComparator);
        System.out.println(Arrays.toString(players));
    }
}

```

```



#### Error stacktrace:

```
com.thoughtworks.qdox.parser.impl.Parser.yyerror(Parser.java:2025)
	com.thoughtworks.qdox.parser.impl.Parser.yyparse(Parser.java:2147)
	com.thoughtworks.qdox.parser.impl.Parser.parse(Parser.java:2006)
	com.thoughtworks.qdox.library.SourceLibrary.parse(SourceLibrary.java:232)
	com.thoughtworks.qdox.library.SourceLibrary.parse(SourceLibrary.java:190)
	com.thoughtworks.qdox.library.SourceLibrary.addSource(SourceLibrary.java:94)
	com.thoughtworks.qdox.library.SourceLibrary.addSource(SourceLibrary.java:89)
	com.thoughtworks.qdox.library.SortedClassLibraryBuilder.addSource(SortedClassLibraryBuilder.java:162)
	com.thoughtworks.qdox.JavaProjectBuilder.addSource(JavaProjectBuilder.java:174)
	scala.meta.internal.mtags.JavaMtags.indexRoot(JavaMtags.scala:49)
	scala.meta.internal.metals.SemanticdbDefinition$.foreachWithReturnMtags(SemanticdbDefinition.scala:99)
	scala.meta.internal.metals.Indexer.indexSourceFile(Indexer.scala:489)
	scala.meta.internal.metals.Indexer.$anonfun$reindexWorkspaceSources$3(Indexer.scala:587)
	scala.meta.internal.metals.Indexer.$anonfun$reindexWorkspaceSources$3$adapted(Indexer.scala:584)
	scala.collection.IterableOnceOps.foreach(IterableOnce.scala:619)
	scala.collection.IterableOnceOps.foreach$(IterableOnce.scala:617)
	scala.collection.AbstractIterator.foreach(Iterator.scala:1306)
	scala.meta.internal.metals.Indexer.reindexWorkspaceSources(Indexer.scala:584)
	scala.meta.internal.metals.MetalsLspService.$anonfun$onChange$2(MetalsLspService.scala:916)
	scala.runtime.java8.JFunction0$mcV$sp.apply(JFunction0$mcV$sp.scala:18)
	scala.concurrent.Future$.$anonfun$apply$1(Future.scala:687)
	scala.concurrent.impl.Promise$Transformation.run(Promise.scala:467)
	java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1095)
	java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:619)
	java.base/java.lang.Thread.run(Thread.java:1447)
```
#### Short summary: 

QDox parse error in file:///C:/Users/panlu/Documents/Cours/M1_Program_Fonction/java/app/bin/main/org/example/cours2/Cours2.java