@GetMapping
public List<Map<String, Object>> getAllItems() {
    List<items> items = itemRepository.findAll();
    List<Map<String, Object>> response = new ArrayList<>();

    for (items item : items) {
        Map<String, Object> itemMap = new HashMap<>();
        itemMap.put("id", item.getId());
        itemMap.put("title", item.getTitle());
        itemMap.put("description", item.getDescription());

        if (item.getImage() != null) {
            // Default to JPEG if the MIME type is not explicitly known
            String base64Image = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(item.getImage());
            itemMap.put("image", base64Image);
        } else {
            itemMap.put("image", null);
        }

        response.add(itemMap);
    }

    return response;
}



import { takeEvery, put, call } from "redux-saga/effects";
import { setTodos } from "./todoActions";

// Worker saga that handles the API call
function* fetchTodosSaga() {
  try {
    // Dispatch to indicate loading has started
    yield put({ type: "FETCH_TODOS_REQUEST" });

    const response = yield call(fetch, "https://jsonplaceholder.typicode.com/todos?_limit=5");
    const todos = yield response.json();
    const todoTitles = todos.map((todo) => todo.title);

    // Dispatch to set todos and stop the loading state
    yield put(setTodos(todoTitles));
  } catch (error) {
    console.error("Error fetching todos:", error);
  }
}

// Watcher saga listens for FETCH_TODOS_REQUEST action
function* rootSaga() {
  yield takeEvery("FETCH_TODOS_REQUEST", fetchTodosSaga);
}

export default rootSaga;
