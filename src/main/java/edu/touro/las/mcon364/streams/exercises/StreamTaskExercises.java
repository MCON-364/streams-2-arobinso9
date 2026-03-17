package edu.touro.las.mcon364.streams.exercises;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Stream practice focused on collecting, grouping, and partitioning.
 *
 * Implement each method using streams.
 * Don't use loops.
 */
public class StreamTaskExercises {

    /**
     * 1)
     * Basics refresher:
     * Return the descriptions of all HIGH priority tasks in encounter order.
     */
    public List<String> highPriorityDescriptions(List<Task> tasks) {
        return tasks.stream()
                .filter(t -> t.priority() == Priority.HIGH)
                // map is a transformer. It looks at each Task record in the stream and takes out only the description string.
                //So- the stream changes from a Stream<Task> to a Stream<String>.
                .map(Task::description)
                .collect(Collectors.toList());
    }

    /**
     * 2)
     * Collecting + grouping:
     * Return the number of tasks in each status.
     */
    public Map<Status, Long> countByStatus(List<Task> tasks) {
        return tasks.stream()
                .collect(Collectors.groupingBy(Task::status, Collectors.counting()));
    }

    /**
     * 3)
     * Grouping + downstream mapping:
     * Group tasks by priority, but keep only task descriptions.
     */
    public Map<Priority, List<String>> descriptionsByPriority(List<Task> tasks) {
        // .mapping() transforms the value in the map. - so we get a task description instead of a task
        return tasks.stream()
                .collect(Collectors.groupingBy(
                        Task::priority,
                        Collectors.mapping(Task::description, Collectors.toList())
                ));
    }

    /**
     * 4)
     * Partitioning:
     * Partition tasks into DONE and not DONE.
     * The map keys should be true and false.
     */
    public Map<Boolean, List<Task>> partitionByDone(List<Task> tasks) {
        return tasks.stream()
                .collect(Collectors.partitioningBy(t-> t.status().equals(Status.DONE)));
    }

    /**
     * 5)
     * Partitioning + downstream counting:
     * Count how many tasks are DONE vs not DONE.
     */
    public Map<Boolean, Long> countDonePartition(List<Task> tasks) {
        return tasks.stream()
                .collect(Collectors.partitioningBy(t-> t.status().equals(Status.DONE), Collectors.counting()));
    }

    /**
     * 6)
     * Nested grouping:
     * First group by status, then by priority.
     *
     * When we use Collectors.groupingBy(Key, Downstream), the Downstream part tells Java what to do
     * with the items after they've been sorted into the first bucket.
     *
     * The Outer Layer: groupingBy(Task::status, ...) creates the primary Map
     * The keys are Status objects, The values are waiting to be processed by the next collector.
     *
     * The Inner Layer: groupingBy(Task::priority)
     * Instead of just making a List<Task> for each Status, Java runs another grouping operation on the tasks that landed in that Status bucket.
     *
     * So now we have a secondary Map inside the first one.
     */
    public Map<Status, Map<Priority, List<Task>>> groupByStatusThenPriority(List<Task> tasks) {
        return tasks.stream()
                .collect(Collectors.groupingBy(Task::status, Collectors.groupingBy(Task::priority)));
    }

    /**
     * 7)
     * Grouping + mapping + collectingAndThen:
     * Group by status and return alphabetically sorted descriptions for each status.
     */
    public Map<Status, List<String>> sortedDescriptionsByStatus(List<Task> tasks) {
        return tasks.stream().collect(
                Collectors.groupingBy(
                        Task::status, // 1. The Key
                        Collectors.mapping(
                                Task::description, // 2. Extract the String
                                Collectors.collectingAndThen(
                                        Collectors.toList(), // 3a. Gather into a List
                                        list -> {
                                            list.sort(null); // 3b. Sort the List And Then return it
                                            return list;
                                        }
                                )
                        )
                )
        );
    }

    /**
     * 8)
     * Challenge:
     * Return a comma-separated string of descriptions for DONE tasks,
     * preserving encounter order.
     *
     * Example: "Write syllabus, Grade quizzes"
     */
    public String doneTaskSummary(List<Task> tasks) {
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * 9)
     * flatMap:
     * Return all tags from all work items in encounter order.
     */
    public List<String> allTags(List<WorkItem> items) {
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * 10)
     * flatMap + distinct:
     * Return distinct assignees for DONE items in encounter order.
     */
    public List<String> distinctDoneAssignees(List<WorkItem> items) {
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * 11)
     * toMap:
     * Build a map from work-item id to status.
     */
    public Map<String, Status> idToStatus(List<WorkItem> items) {
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * 12)
     * groupingBy + mapping:
     * Group by priority and collect only titles.
     */
    public Map<Priority, List<String>> titlesByPriorityUsingMapping(List<WorkItem> items) {
        throw new UnsupportedOperationException("TODO");
    }
}
