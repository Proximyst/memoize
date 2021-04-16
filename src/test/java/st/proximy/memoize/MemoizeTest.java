//
// memoize - Memoize the return values of an expensive, dynamic state method.
// Copyright (C) 2021 Mariell Hoversholm
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published
// by the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with this program.  If not, see <https://www.gnu.org/licenses/>.
//

package st.proximy.memoize;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.Supplier;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.junit.jupiter.api.Test;

class MemoizeTest {
  private final CountingSupplier<String> supplier = new CountingSupplier<>(() -> "test string");

  @Test
  void testMemoize() {
    final Memoizable<String> memoizable = new MemoizableObject<>(this.supplier);

    assertThat(this.supplier.count()).as("no interactions yet").isZero();
    memoizable.memoized();
    assertThat(this.supplier.count()).as("no further interaction").isOne();
    memoizable.memoized();
    assertThat(this.supplier.count()).as("no further interaction").isOne();
    assertThat(memoizable.memoized()).isSameAs(this.supplier.get());
  }

  @Test
  void testEagerMemoization() {
    final Memoizable<String> memoizable = new MemoizableObject<>(this.supplier).eager();

    assertThat(this.supplier.count()).as("single interaction").isOne();
    memoizable.memoized();
    assertThat(this.supplier.count()).as("no further interaction").isOne();
    assertThat(memoizable.memoized()).isSameAs(this.supplier.get());
  }

  private static final class CountingSupplier<T> implements Supplier<T> {
    private final @NonNull Supplier<T> supplier;
    private int count = 0;

    private CountingSupplier(final @NonNull Supplier<T> supplier) {
      this.supplier = supplier;
    }

    @Override
    public T get() {
      ++this.count;
      return this.supplier.get();
    }

    public int count() {
      return this.count;
    }
  }
}
